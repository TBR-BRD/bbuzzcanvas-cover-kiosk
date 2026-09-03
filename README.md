# BBuzzCanvas Cover Kiosk

![Android](https://img.shields.io/badge/Android-5.1-3DDC84?logo=android&logoColor=white)
![Kiosk](https://img.shields.io/badge/Mode-Fullscreen%20Kiosk-222222)
![WebView](https://img.shields.io/badge/UI-Android%20WebView-4285F4)
![GitHub Actions](https://img.shields.io/badge/Build-GitHub%20Actions-2088FF?logo=githubactions&logoColor=white)

Android-5.1-kompatible Kiosk-App für ein BBuzzCanvas als zusätzliches Fullscreen-Coverdisplay für das Projekt **OnRadio Cover Bridge**.

Die App zeigt ausschließlich die vom Raspberry Pi bereitgestellte Cover-Seite im Vollbild an.

## BBuzzCanvas

![BBuzzCanvas Digital Art Display](docs/media/bbuzzcanvas.png)

Das BBuzzCanvas wird als zusätzliches Display verwendet. Die eigentliche Radio-, Cover- und Metadatenlogik läuft auf dem Raspberry Pi.

Backend-Projekt:

https://github.com/TBR-BRD/onradio-cover-bridge

## Empfohlene URL

Für das verwendete BBuzzCanvas ist folgende URL vorgesehen:

```text
http://<PI-IP>:8080/cover?rotate=left&overlay=1
```

Bedeutung:

- `rotate=left` dreht die Darstellung passend zur Einbaulage des BBuzzCanvas.
- `overlay=1` blendet Interpret und Titel dezent unten rechts ein.

Ohne Overlay:

```text
http://<PI-IP>:8080/cover?rotate=left
```

Ohne Rotation und Overlay:

```text
http://<PI-IP>:8080/cover
```

## Funktionen

- Android 5.1 kompatibel
- Fullscreen-/Immersive-Kiosk
- Status- und Navigationsleiste ausgeblendet
- Bildschirm bleibt eingeschaltet
- Cover-URL beim ersten Start konfigurierbar
- URL später per Langdruck auf das Display änderbar
- automatische Wiederverbindung bei Ladefehlern
- Autostart nach Android-Boot
- keine private IP-Adresse fest im Quellcode
- Anzeige des aktuellen Albumcovers
- optionales Interpret-/Titel-Overlay über die Weboberfläche
- keine Anpassung der APK nötig, wenn sich das Overlay auf der `/cover`-Seite ändert

## Funktionsweise

```text
                    WLAN / LAN
                        |
                        v
             +----------------------+
             |    Raspberry Pi 3    |
             |  OnRadio Cover Bridge|
             |----------------------|
             | FastAPI Webserver    |
             | Cover-Auflösung      |
             | Metadaten            |
             | Cover-Proxy          |
             +----------+-----------+
                        |
                        | HTTP
                        v
             +----------------------+
             |     BBuzzCanvas      |
             |      Android 5.1     |
             |----------------------|
             | BBuzz Cover Kiosk    |
             | Android WebView      |
             | Fullscreen           |
             +----------------------+
```

Die Android-App selbst enthält keine Radio- oder Coverlogik. Sie lädt ausschließlich die konfigurierte URL des Raspberry Pi.

## Erste Inbetriebnahme

1. APK auf das BBuzzCanvas kopieren.
2. Installation aus unbekannten Quellen erlauben, falls Android danach fragt.
3. APK installieren.
4. App **BBuzz Cover** starten.
5. Cover-URL eingeben:

```text
http://<PI-IP>:8080/cover?rotate=left&overlay=1
```

6. Speichern.
7. Die Cover-Seite wird im Fullscreen-Modus angezeigt.

## URL später ändern

Auf dem angezeigten Cover **lange drücken**.

Danach öffnet sich die URL-Konfiguration erneut.

## APK installieren

Die per GitHub Actions erzeugte APK heißt typischerweise:

```text
app-debug.apk
```

Sie kann beispielsweise per:

- USB-Stick
- SD-Karte
- Android-Dateimanager
- ADB

auf das BBuzzCanvas übertragen werden.

### Installation per ADB

Falls ADB verfügbar ist:

```bash
adb install -r app-debug.apk
```

App starten:

```bash
adb shell monkey -p de.onradio.bbuzzcover 1
```

## APK ohne Android Studio bauen

Für den Build wird kein Android Studio benötigt.

Das Repository enthält einen GitHub-Actions-Workflow:

```text
.github/workflows/build-apk.yml
```

Nach einem Push auf `main` startet der Build automatisch.

Auf GitHub:

```text
Actions
→ Build Android APK
→ erfolgreicher Build
→ Artifacts
→ BBuzzCanvasCoverKiosk
```

Das Artifact herunterladen und entpacken.

Darin befindet sich die APK:

```text
app-debug.apk
```

## GitHub Push

Änderungen lokal committen und hochladen:

```bash
git status
git add .
git commit -m "Update BBuzzCanvas Cover Kiosk"
git push
```

## Projektstruktur

```text
BBuzzCanvasCoverKiosk/
├── .github/
│   └── workflows/
│       └── build-apk.yml
├── app/
│   ├── build.gradle
│   └── src/
│       └── main/
│           ├── AndroidManifest.xml
│           ├── java/
│           │   └── de/onradio/bbuzzcover/
│           │       ├── MainActivity.java
│           │       └── BootReceiver.java
│           └── res/
│               └── values/
│                   ├── strings.xml
│                   └── styles.xml
├── docs/
│   └── media/
│       └── bbuzzcanvas.png
├── build.gradle
├── gradle.properties
├── settings.gradle
└── README.md
```

## Verbindung zum OnRadio Cover Bridge

Die Weboberfläche `/cover`, das Albumcover, Interpret und Titel werden vom Raspberry-Pi-Projekt bereitgestellt:

https://github.com/TBR-BRD/onradio-cover-bridge

Wichtige Endpunkte:

| Funktion | URL |
| --- | --- |
| Cover | `http://<PI-IP>:8080/cover` |
| Cover gedreht | `http://<PI-IP>:8080/cover?rotate=left` |
| Cover + Overlay | `http://<PI-IP>:8080/cover?rotate=left&overlay=1` |
| Status-API | `http://<PI-IP>:8080/api/state` |

## Datenschutz

- Keine privaten IP-Adressen sollten im öffentlichen Repository gespeichert werden.
- Beispiel-URLs verwenden deshalb `<PI-IP>`.
- Keine Passwörter, Tokens oder Zugangsdaten im Repository speichern.
- Gerätespezifische Einstellungen werden lokal auf dem BBuzzCanvas gespeichert.

## Lizenz / externe Inhalte

Eigene Projektdateien können unter einer passenden Open-Source-Lizenz veröffentlicht werden.

Das BBuzzCanvas, externe Logos, Albumcover, Sendernamen und sonstige Medieninhalte unterliegen den jeweiligen Rechten ihrer Anbieter.
