import getopt
import shutil
import subprocess as sp
import sys
import warnings


def main(argv):
    opts, args = getopt.getopt(argv, "hrf", ["help", "run", "fix"])
    if len(opts) == 0:
        opts = [("--help", None)]

    for opt, arg in opts:
        if opt in ("-h", "--help"):
            print("""
            -h/--help - This page
            -r/--run - Run db
            -f/--fix - Rebuild db""")
        elif opt in ("-r", "--run"):
            sp.run(["docker", "compose", "up", "--build"])
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
            sp.run(["docker", "compose", "up", "--build"])


if __name__ == "__main__":
    main(sys.argv[1:])
