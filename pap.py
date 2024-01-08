import getopt
import shutil
import subprocess as sp
import sys
import warnings
import os

RUN_DB_CMD = ["docker", "compose", "up", "--build"]


def main(argv):
    opts, args = getopt.getopt(argv, "hdif", ["help", "database", "install", "fix"])
    if len(opts) == 0:
        opts = [("--help", None)]

    for opt, arg in opts:
        if opt in ("-h", "--help"):
            print("""
            -h/--help - This page
            -d/--database - Run database
            -i/--install - Setup app for running
            -f/--fix - Rebuild the database"""
            )
        elif opt in ("-d", "--database"):
            sp.run(RUN_DB_CMD)
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
            sp.run(RUN_DB_CMD)
        elif opt in ("-i", "--install"):
            if os.path.exists("/usr/bin/apt"):
                package_manger = "apt"

                # Add Liberica repositories
                os.system(
                    """wget -q -O - https://download.bell-sw.com/pki/GPG-KEY-bellsoft | sudo apt-key add -"""
                )
                os.system(
                    """echo "deb https://apt.bell-sw.com/ stable main" | sudo tee /etc/apt/sources.list.d/bellsoft.list"""
                )

            elif os.path.exists("/usr/bing/dnf"):
                package_manger = "dnf"

                # Add Liberica repositories
                os.system(
                    """echo | sudo tee /etc/yum.repos.d/bellsoft.repo > /dev/null << EOF
                    [BELLSOFT]
                    name=BELLSOFT Repository
                    baseurl=https://yum.bell-sw.com
                    enabled=1
                    gpgcheck=1
                    gpgkey=https://download.bell-sw.com/pki/GPG-KEY-bellsoft
                    priority=1
                    EOF"""
                )

            # Install docker & docker-compose & liberica17
            sp.run(["sudo", package_manger, "update"])
            sp.run(
                [
                    "sudo",
                    package_manger,
                    "install",
                    "docker",
                    "docker-compose",
                    "bellsoft-java17-full",
                ]
            )

            # Database in the background
            sp.Popen(RUN_DB_CMD)

            # Run the app
            liberica = "/usr/lib/jvm/bellsoft-java17-full-amd64/bin/java"
            sp.run([liberica, "-jar", "pap.jar"])


if __name__ == "__main__":
    main(sys.argv[1:])
