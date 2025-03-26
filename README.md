# JavaFX-Cubic-Spline
Java based cubic spline interpolation with JavaFX user interface.

## About the program

This program can show **four** different types of cubic spline interpolation within eight nodes.

Types of common boundary conditions:
- Clamped (fixed)
- Natural (soft)
- Cyclic
- Acyclic

[Boundary conditions](https://en.wikiversity.org/wiki/Cubic_Spline_Interpolation#nogo)

## System requirements
Java version 23.0.1  
JavaFX version 23.0.1

800x600 pixels screen

## Compilation

### Windows

1. Download latest [JDK](https://download.oracle.com/java/24/latest/jdk-24_windows-x64_bin.exe) and install it (adding variables to PATH).

2. Make sure you have javac.exe compiler `C:\Users\USER\.jdks\jdk-{version}\bin\javac.exe`

3. Download latest [JavaFx](https://gluonhq.com/products/javafx/) library and extract contents whereever it is reachable.

4. To compile, open command line in __source code directory__ and write  
   `javac.exe --module-path {Path to your javafx lib folder} --add-modules=javafx.controls Main.java`

    - For example if JavaFx contents extracted to `С:\Java`
      Then command should look like this:  
      `javac.exe --module-path "C:\Java\javafx-sdk-23.0.1\lib" --add-modules=javafx.controls Main.java` 
    - If you have problems with javac.exe, try to write __absolute path__ to javac.exe (see p.2)
      

5. After compilation run `Main` file:  
   `java --module-path {Path to your javafx lib folder} --add-modules=javafx.controls Main`

### Linux (Debian/Ubuntu)

1. Run `sudo apt update` and `sudo apt upgrade`

2. Download latest [JDK](https://www.oracle.com/java/technologies/downloads/) for your Linux system and install it.

3. Download latest [JavaFx](https://gluonhq.com/products/javafx/) library and extract contents whereever it is reachable.

4. To compile, open command line in __source code directory__ and write  
   `javac --module-path {Path to your javafx lib folder} --add-modules=javafx.controls Main.java`

   - For example if JavaFx contents extracted to `home/USER/Java`
      Then command should look like this:  
      `javac --module-path "home/USER/Java/javafx-sdk-23.0.1/lib" --add-modules=javafx.controls Main.java`

5. After compilation run `Main` file:  
   `java --module-path {Path to your javafx lib folder} --add-modules=javafx.controls Main`
   
## How to use

After successful compilation you will see something like this:

![Figure 1. Start screen](https://github.com/titemov/JavaFX-Cubic-Spline/blob/main/images/start_screen.png)

It is possible to change each node coordinate within 20x20 Cartesian coordinate system. To do such thing you need to enter coordinate in exact text field and then condirm your choise by pressing __"Enter!"__ button. Nodes will move.

Or you can just press randomization button __"Random"__.

>[!NOTE]
>Two different nodes can not have same X and Y coordinate

After that you can choose type of cubic spline interpolation by opening a dropdown menu like this:

![Figure 2. Dropdown menu](https://github.com/titemov/JavaFX-Cubic-Spline/blob/main/images/dropdown_menu.png)

After choosing boundary type just press big button __"Calculate!"__. By pressing that program will calculate cubic spline interpolation.

![Figure 3. Results](https://github.com/titemov/JavaFX-Cubic-Spline/blob/main/images/result.png)

>[!TIP]
>Fixed (clamped) boundary condition implies that endpoint vectors are known. To enter them use four text fields just below dropdown menu. By default endpoint vectors are `(0;0)` and `(0;0)`

To erase cubic spline just press __"Clear"__ button on top right of the screen.

## Known issues

Sometimes due to simplicity of interpolation ("Loaded" spline method is not used) it is possible to go beyond the coordinate system towards the interface buttons. The program will indicate that some parts of the lines are not drawn.

![Figure 4. Out of bounds](https://github.com/titemov/JavaFX-Cubic-Spline/blob/main/images/Issue.png)

If you see such a thing try to switch to "Fixed" type of boundary condition or just regenerate set of nodes.

## References

- Mathematical Elements for Computer Graphics. David F. Rodgers, James Alan Adams. ISBN	0070535272.

