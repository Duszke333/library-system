import getopt
import shutil
import subprocess as sp
import sys
import warnings

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
            -f/--fix - Rebuild the database""")
        elif opt in ("-d", "--database"):
            sp.run(RUN_DB_CMD)
        elif opt in ("-f", "--fix"):
            warnings.warn("It may be necessary to run script in superuser mode (Linux)")
            warnings.warn("This script only removes database disk space. YOU MAY WANT TO REMOVE DOCKER IMAGE MANUALLY")
            warnings.warn("Make sure to run init.sql after 'Database is ready to accept connections'")
            answer = input("Proceed? [Y/N]\t")
            if answer.upper() not in ("Y", "YES"):
                return

            # DROP EVERYTHING
            shutil.rmtree("./var/")

            # Build everything from the ground up
            sp.run(RUN_DB_CMD)
        elif opt in ("-i", "--install"):
            # Database in the background
            sp.Popen(RUN_DB_CMD)

            # Run the app
            sp.run(["java", "-jar", "pap.jar"])


if __name__ == "__main__":
    main(sys.argv[1:])
