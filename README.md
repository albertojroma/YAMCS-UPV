# Guía de uso de YAMCS para el proyecto UniClOGS

El objetivo de esta guía es facilitar el arranque de la versión de YAMCS para UniClOGS en sus diferentes versiones.

## 1. Entendiendo la Arquitectura (El Diagrama ICD)
Antes de instalar nada, el `README.md` nos remite al diagrama `uniclogs-yamcs-fbd.png`:
* **`uniclogs-sdr`:** Actúa como el satélite o la estación terrena (el equivalente a tu antiguo script `simulator.py`).
* **Enlaces de datos:** Envía Telemetría (TM) a Yamcs por el **puerto UDP 10015** y recibe comandos (TC Req) por el **puerto UDP 10025** (misma configuración que en el training)
* **Herramientas externas:** Yamcs expone sus datos a través de una API en el puerto **TCP 8090**, permitiendo que se conecten herramientas visuales como *Grafana* y su propia terminal *cmd-shell*.

---

## 2. Preparación del Entorno ("Development Quick Start")
Para trastear con los archivos `.yaml` y `.xml` se debe seguir la sección **Development Quick Start**:

* **Inicializar Submódulos:** la base de datos no está en este repo directamente. Se debe ejecutar este comando para que Git se descargue la configuración real desde el otro repositorio:
  `git submodule update --init --recursive`
* **Prerrequisito:** Tener instalado Java 17 o superior (`Java JDK >= 17`).

---

## 3. Arranque del Servidor
A diferencia de AcubeSAT, donde se usaba `mvn yamcs:run`, el `README.md` de UniClOGS nos pide ejecutar:
`mvn clean yamcs:debug`

Esto borra archivos temporales antiguos (`clean`) y lanza el servidor en modo depuración (`debug`).

Una vez arrancado, abrir `http://localhost:8090/` en el navegador.
* **Nota importante del README:** En este modo de desarrollo, el sistema de seguridad está desactivado. Si por un fallo la web te redirige a una pantalla de error en `/auth/authorize` simplemente se escribe `http://localhost:8090/` en la barra de direcciones.

---

## 4. Uso de su Terminal Personalizada ("Command Shell Usage")
El proyecto UniClOGS tiene una herramienta externa en Python para enviar comandos, que se comunica con Yamcs a través de la API del puerto 8090. Para usarla, el README indica:

1. Instalar las librerías de Python necesarias ejecutando (recomendable usar un entorno **conda**): 
   `pip install -r cmd_shell/requirements.txt`
2. Asegurarte de que Yamcs está encendido (el paso 3).
3. Lanzar la consola ejecutando: 
   `python3 -m cmd_shell`

:eye: Se ve que la consola usa el mismo puerto que una de las instancias del proyecto (`oresat0_5` concretamente). Además, es difícil de iniciar, se debe iniciar primero el servidor con `mvn clean yamcs:debug` y rápidamente ejecutar `python3 -m cmd_shell` en otra consola hasta que funcione.

---

## (Opcional) La vía Docker ("Installation and Usage")

**Nota**: para este paso es necesario tener instalado `docker` en nuestra máquina. Para ello ejecutamos `sudo snap install docker` en el terminal.

El README también dedica su primera sección a **Docker**. Si solo se quisiera ejecutar Yamcs en un servidor real sin modificar código, se ejecutarían los siguientes comandos:

1. Ejecutar «make» en el directorio raíz del proyecto (en `.../yamcs$`)

   `$` `make`

2. Vamos al archivo `security.yaml` que está en la ruta `yamcs/src/main/yamcs/etc/prod` y comentamos todas las líneas de código. 

Esto se hace porque este proyecto incluye registro mediante usuario y contraseña. Este registro en la nueva versión está incluido, pero como este proyecto se diseño en una versión de YAMCS previa, este registro se realizaba mediante un *plug-in* y cuando se levanta el servidor se detecta que esa herramienta está duplicada y no lo levanta. Por ello, se desactiva el *plug-in*.

3. Acceder al directorio de Docker

  `$` `cd dist/docker`

4. Compilar mediante Docker-Compose

  `$` `sudo docker compose build`

5. Comprobar que se ha compilado la imagen `uniclogs-yamcs`

  `$` `sudo docker images`

6. Lanzar servidor

  `$` `sudo docker compose up`

* **Ojo al dato:** Si usas la versión Docker, el módulo de seguridad **sí** está activado, y el README especifica que las credenciales por defecto son usuario `admin` y contraseña `admin`.

**¿Por dónde deberías empezar?**
Es recomendable hacer el `git submodule update...`, abrir el código y luego ejcutar `mvn clean yamcs:debug` para ver qué pinta tiene la base de datos de un proyecto real en la interfaz web. 