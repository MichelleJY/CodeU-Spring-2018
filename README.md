# Some Lifesaving Commands


## Git

git add .   //stages all the files in the current directory you're in
git add filename.java   //stages specific file(s)
git reset   //removes all staged objects
git commit -m "some comment here"   //commit your changes
git diff    //see non-staged/non-added changes to existing files
git status  //get current status
git checkout origin/branchnamehere //if waiting for a merge, can use that branch's code

## Maven

mvn clean appengine: devserver    //runs locally

You should now be able to use a local version of the chat app by opening your
browser to [http://localhost:8080](http://localhost:8080).

mvn test    //just runs the tests





