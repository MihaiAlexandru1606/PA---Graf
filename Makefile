build:
	javac *.java

run-p1:
	java Minlexbfs

run-p2:
	java -Xss128M Disjcnt

run-p3:
	java Rtd

run-p4:
	java Revedges

clean:
	rm -fr *.class
