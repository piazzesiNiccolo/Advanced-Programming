# Exercise 2
Check the differences between files `StrangeOne.java` and `StrangeTwo.java`. Compile them, disassemble the obtained classes using `javap -c` and inspect the byte-code of the method sum. Is the byte-code the same? Can you explain why?

**Goal**: Using `javap` for disassembling Java code; inspecting simple bytecodes.

**Expected output**: One short sentence answering the two questions.

# Analysis
By analysing the two classes we can see that they only differ for the type of the fields b0 and b1. In StrangeOne they are of type <b> int</b> while in StrangeTwo the are of type <b>byte</b>.
Disassembling the classes gives the following results:

```
class StrangeOne {
  int b0;

  int b1;

  StrangeOne();
    Code:
       0: aload_0
       1: invokespecial #1                  // Method java/lang/Object."<init>":()V
       4: aload_0
       5: bipush        42
       7: putfield      #2                  // Field b0:I
      10: aload_0
      11: bipush        42
      13: putfield      #3                  // Field b1:I
      16: return

  int sum();
    Code:
       0: aload_0
       1: getfield      #2                  // Field b0:I
       4: aload_0
       5: getfield      #3                  // Field b1:I
       8: iadd
       9: ireturn
}
```

```
class StrangeTwo {
  byte b0;

  byte b1;

  StrangeTwo();
    Code:
       0: aload_0
       1: invokespecial #1                  // Method java/lang/Object."<init>":()V
       4: aload_0
       5: bipush        42
       7: putfield      #2                  // Field b0:B
      10: aload_0
      11: bipush        42
      13: putfield      #3                  // Field b1:B
      16: return

  int sum();
    Code:
       0: aload_0
       1: getfield      #2                  // Field b0:B
       4: aload_0
       5: getfield      #3                  // Field b1:B
       8: iadd
       9: ireturn
}
```

As we can see we have an identical result. The JVM has a limited number of opcodes available (255) and so, by design, has almost no support for direct operations on bytes, using int as a computational type. This is why the two classes compile to the same bytecode.