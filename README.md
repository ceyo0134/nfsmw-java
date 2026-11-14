# NFS Most Wanted (J2ME) - build + web port

Game code (`src/main/java/TheGame.java`, `AppMain.java`) is untouched.

- `dist/NFSMW.jar`: working build for J2ME Loader (Java 6 bytecode; the Java 8 builds gave "Dexing error").
- `./build.sh` (JDK 8) or the GitHub Actions workflow rebuilds it.

## Web port status
Not done yet. `src/main/java/javax/**` are compile-time stubs only (e.g. `Graphics3D.getInstance()` returns null),
so a browser needs a real MIDP (Canvas/keys/RMS) and M3G (WebGL) runtime. Planned order:
1. MIDP layer on HTML canvas (menus, 2D, input, saves)
2. M3G loader for `.m3g` / `.fm3d` + WebGL renderer
