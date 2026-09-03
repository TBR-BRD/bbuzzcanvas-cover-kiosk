# BBuzzCanvas Cover Kiosk

Android-5.1-kompatible Kiosk-App für das BBuzzCanvas.

Sie zeigt ausschließlich die Cover-Seite des Raspberry-Pi-Radios:

```text
http://<PI-IP>:8080/cover
```

## BBuzzCanvas

![BBuzzCanvas Digital Art Display](docs/media/bbuzzcanvas.png)

Das BBuzzCanvas wird mit einer kleinen Android-Kiosk-App als zusätzliches Fullscreen-Coverdisplay verwendet.

Die App zeigt ausschließlich die Cover-Seite des Raspberry-Pi-Radios:

`http://<PI-IP>:8080/cover`

Für das hier verwendete Gerät ist wegen der Displayausrichtung folgende Variante geeignet:

`http://<PI-IP>:8080/cover?rotate=left`

### Funktionen

- Android 5.1 kompatibel
- Fullscreen-/Immersive-Kiosk
- Status- und Navigationsleiste ausgeblendet
- Bildschirm bleibt eingeschaltet
- automatische Wiederverbindung bei Netzwerkfehlern
- Start nach Android-Boot
- Cover-URL beim ersten Start konfigurierbar
- URL später per Langdruck auf das Display änderbar
- keine private IP-Adresse fest im Quellcode hinterlegt

### Backend / Raspberry-Pi-Projekt

Die Cover-Seite und die Radio-Logik werden vom Raspberry-Pi-Projekt bereitgestellt:

https://github.com/TBR-BRD/onradio-cover-bridge
