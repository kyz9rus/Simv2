# Lab Character calculations #2

This application calculates matrix expressions. It can be different operations such as add, subtract, multiplicate e.t.c (see below).

To calc you need to write expression to input.xml file (in directory simv2.2) write as expression tree (example of this in file). There are some files/examples, which show how it should look.

There are operations:
- add - addition matrices;
- sub - suntract matrices;
- mul - multiply matrices;
- mulNumber - multiply matrix by number;
- expontiate - raise matrix to power;
- transpose - transpose matrix;

Bynary operations are worked with not only 2 operands, there are a lot of matrices.
This application also works with nested matrices.

To run application just open this project in your IDE and run Main.main(). The result will be in file output.xhtml.

If you use IDEA you can edit configuration and add launch the browser after building (you need to set url to output.xhtml file in the IDEA window (it can be http://localhost:63342/simv2.2/output.xhtml))

---
If for different reasons IDE can't find the main class -> go to project settings and mark src folder as source.

Also you can get error "invalid soure release: number" -> check in module settings: project sdk and project level version must have the same java version.

Be sure that path to project compiler output set to folder "out" in root directory.
