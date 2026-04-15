# ¿Qué es YAMCS?

**YAMCS** (*Yet Another Mission Control System*) es un software de código abierto de grado profesional diseñado para controlar y monitorizar misiones espaciales. Actúa como el "cerebro" o centro de operaciones en tierra (Ground Segment) para satélites, constelaciones, cargas útiles e incluso robótica avanzada.

Aunque nació en el ámbito de las operaciones a bordo de la Estación Espacial Internacional (ISS), hoy en día es utilizado por agencias espaciales (como la ESA), empresas comerciales y equipos universitarios de CubeSats gracias a su flexibilidad y potencia.


## Funciones Principales

YAMCS se encarga de gestionar el ciclo de vida completo de los datos que viajan entre la Tierra y el espacio:

* **Recepción de Telemetría (TM)**: Escucha, decodifica y traduce el flujo de datos crudos (ceros y unos) que envía el satélite en información legible por humanos (temperaturas, voltajes, estado de los sistemas).
* **Envío de Telecomandos (TC)**: Permite a los operadores empaquetar órdenes y enviarlas de forma segura al vehículo espacial para que ejecute acciones.
* **Archivo Histórico**: Funciona como una "caja negra" masiva. Guarda absolutamente todos los datos de telemetría, comandos enviados y eventos del sistema desde el día 1 de la misión, permitiendo búsquedas y análisis retrospectivos.
* **Gestión de Alarmas**: Monitorea los parámetros en tiempo real y dispara alertas si algo sale de los límites de seguridad definidos.
* **Eventos**: YAMCS no solo gestiona números (parámetros), sino también mensajes de texto (logs) que envía el satélite o el propio servidor. Es muy útil para depurar.

## ¿Por qué elegir YAMCS?

Existen varios sistemas de control de misiones, pero YAMCS destaca por las siguientes características:

* **Código Abierto:** Es gratuito, auditable y cuenta con una comunidad activa de desarrolladores respaldada por *Space Applications Services*.
* **Basado en Estándares Espaciales:** Habla el "idioma" del espacio. Soporta de forma nativa los estándares de empaquetado CCSDS y el estándar XTCE para definir la base de datos de la misión (el diccionario de telemetría y comandos).
* **Arquitectura Cliente-Servidor Moderna:** El núcleo (backend) está escrito en Java, lo que lo hace muy robusto y multiplataforma. Los operadores interactúan con él a través de una API REST moderna, websockets o su interfaz web integrada.
* **Escalabilidad:** Puede correr en una simple Raspberry Pi para pruebas de laboratorio, o desplegarse en servidores en la nube para manejar constelaciones de cientos de satélites.

## ¿Cómo interactúas con él?

YAMCS funciona en segundo plano como un servidor. Para ver los datos y controlar la misión, los usuarios utilizan principalmente tres vías:

1.  **YAMCS Web:** Una interfaz directamente en el navegador, ideal para monitorización, envío de comandos básicos y revisión del archivo histórico.
2.  **YAMCS Studio:** Un software de escritorio avanzado para diseñar y visualizar pantallas de control complejas (sinópticos) con gráficas, diales y diagramas del satélite en tiempo real.
3.  **API y Python:** Los ingenieros pueden usar librerías como `python-yamcs-client` para escribir scripts que analicen datos automáticamente o envíen secuencias de comandos complejas sin intervención manual.

## Conceptos Clave para el Desarrollador

Para entender cómo se pone en marcha una misión en YAMCS, hay que visualizar tres pilares fundamentales que trabajan en cascada:

1.  **Instancia:** Es el contenedor lógico de una misión. Cada instancia tiene su propia configuración, sus propios datos y su propio historial, **totalmente aislados de otras misiones** en el mismo servidor.
2.  **MDB (Mission Database):** Es el "Diccionario" de la misión. Aquí es donde se define **mediante archivos XML** (estándar XTCE) qué aspecto tienen los paquetes. Sin una MDB, YAMCS recibe bits pero no sabe si significan "Temperatura" o "Voltaje".
3.  **Links (Enlaces de datos):** Es el "Puente" físico o virtual. Son los encargados de conectar el servidor con el mundo exterior (un socket TCP/UDP, un puerto serie o un simulador).


## La Jerarquía de Configuración (Archivos YAML)

YAMCS no se configura con botones, sino con archivos de texto tipo **YAML**. La estructura sigue este orden:

* **`yamcs.yaml` (Global):** Configura el servidor completo (puertos web, seguridad y qué instancias están activas).
* **`yamcs.[instancia].yaml` (Específico):** Configura los detalles de tu satélite: qué archivos MDB cargar, qué puertos usar para los Links y cómo archivar los datos.