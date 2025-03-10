﻿import getopt
import shutil
import subprocess as sp
import sys
import warnings
import os


def run_db_cmd(package_manager: str):
    if package_manager in ("dnf", "apt"):
        return ["sudo", "docker-compose", "up", "--build"]
    else:
        return ["docker", "compose", "up", "--build"]


def main(argv):
    opts, _ = getopt.getopt(argv, "hdif", ["help", "database", "install", "fix"])
    if len(opts) == 0:
        opts = [("--help", None)]

    if os.path.exists("/usr/bin/apt"):
        package_manager = "apt"
    elif os.path.exists("/usr/bin/dnf"):
        package_manager = "dnf"
    else:
        package_manager = "windows"

    for opt, _ in opts:
        if opt in ("-h", "--help"):
            print(
                """
            -h/--help - This page
            -d/--database - Run database
            -i/--install - Setup app for running
            -f/--fix - Rebuild the database"""
            )
        elif opt in ("-d", "--database"):
            sp.run(run_db_cmd(package_manager))
        elif opt in ("-f", "--fix"):
            warnings.warn("It may be necessary to run script in superuser mode (Linux)")
            warnings.warn(
                "This script only removes database disk space. YOU MAY WANT TO REMOVE DOCKER IMAGE MANUALLY"
            )
            warnings.warn(
                "Make sure to run init.sql after 'Database is ready to accept connections'"
            )
            answer = input("Proceed? [Y/N]\t")
            if answer.upper() not in ("Y", "YES"):
                return

            # DROP EVERYTHING
            shutil.rmtree("./var/")

            # Build everything from the ground up
            sp.run(run_db_cmd(package_manager))
        elif opt in ("-i", "--install"):
            if package_manager == "apt":
                # Add Liberica repositories
                os.system(
                    """wget -q -O - https://download.bell-sw.com/pki/GPG-KEY-bellsoft | sudo apt-key add -"""
                )
                os.system(
                    """echo "deb https://apt.bell-sw.com/ stable main" | sudo tee /etc/apt/sources.list.d/bellsoft.list"""
                )

            elif package_manager == "dnf":
                # Add Liberica repositories
                cmd = "echo | sudo tee /etc/yum.repos.d/bellsoft.repo > /dev/null << EOF\n[BELLSOFT]\nname=BELLSOFT Repository\nbaseurl=https://yum.bell-sw.com\nenabled=1\ngpgcheck=1\ngpgkey=https://download.bell-sw.com/pki/GPG-KEY-bellsoft\npriority=1\nEOF"
                os.system(cmd)

            # Install dependencies
            sp.run(["sudo", package_manager, "update"])
            sp.run(
                [
                    "sudo",
                    package_manager,
                    "install",
                    "docker-compose",
                    "bellsoft-java17-full",
                ]
            )

            # Start docker temporarily
            sp.run(["sudo", "systemctl", "start", "docker.service"])

            # Database in the background
            sp.Popen(run_db_cmd(package_manager))

            # Copy init.sql file
            os.system("sudo docker cp ./Docker/init.sql pap2023z-z17_db:/docker-entrypoint-initdb.d/init.sql")
            # Run init.sql file
            os.system("sudo docker exec pap2023z-z17_db psql -U postgres -f docker-entrypoint-initdb.d/init.sql")

            # Run the app
            liberica = (
                "/usr/lib/jvm/bellsoft-java17-full.x86_64/bin/java"
                if package_manager == "dnf"
                else "/usr/lib/jvm/bellsoft-java17-full-amd64/bin/java"
            )
            sp.run([liberica, "-jar", "pap.jar"])


if __name__ == "__main__":
    main(sys.argv[1:])
