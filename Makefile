CPP=javac


all: main

main : model_class view_class main_class

model_class : Model/*.java
	$(CPP) Model/*.java

view_class : View/*.java
	$(CPP) View/*.java

main_class : Main.java
	$(CPP) Main.java



clean :
	rm Model/*.class
	rm View/*.class
	rm *.class

