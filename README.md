# NFS Most Wanted (J2ME) - build + web port

Game code (`src/main/java/TheGame.java`, `AppMain.java`) is untouched.

- `dist/NFSMW.jar`: working build for J2ME Loader (Java 6 bytecode; the Java 8 builds gave "Dexing error").
- `./build.sh` (JDK 8) or the GitHub Actions workflow rebuilds it.

## Web port status
Done (web/shim, build with web/build-shim.sh):
- Real MIDP layer on java.awt: Display, Canvas (key/repaint loop), Graphics, Image, Font, RecordStore (in-memory).
- Headless test host (web/Harness.java) that starts the MIDlet and dumps frames to PNG.

Blocker found by running it: the game cannot even be constructed because the M3G classes (Transform, Graphics3D, Loader, ...)
call a native library (javam3g) that does not exist outside J2ME Loader. Next step is a pure-Java (then WebGL) M3G
implementation, including the .m3g / .fm3d loader. Game code in src/main/java is untouched.
