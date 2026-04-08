# ¿Qué es YAMCS?

**YAMCS** (*Yet Another Mission Control System*) es un software de código abierto de grado profesional diseñado para controlar y monitorizar misiones espaciales. Actúa como el "cerebro" o centro de operaciones en tierra (Ground Segment) para satélites, constelaciones, cargas útiles e incluso robótica avanzada.

Aunque nació en el ámbito de las operaciones a bordo de la Estación Espacial Internacional (ISS), hoy en día es utilizado por agencias espaciales (como la ESA), empresas comerciales y equipos universitarios de CubeSats gracias a su flexibilidad y potencia.


## Funciones Principales

YAMCS se encarga de gestionar el ciclo de vida completo de los datos que viajan entre la Tierra y el espacio:

* **Recepción de Telemetría (TM):** Escucha, decodifica y traduce el flujo de datos crudos (ceros y unos) que envía el satélite en información legible por humanos (temperaturas, voltajes, estado de los sistemas).
* **Envío de Telecomandos (TC):** Permite a los operadores empaquetar órdenes y enviarlas de forma segura al vehículo espacial para que ejecute acciones.
* **Archivo Histórico:** Funciona como una "caja negra" masiva. Guarda absolutamente todos los datos de telemetría, comandos enviados y eventos del sistema desde el día 1 de la misión, permitiendo búsquedas y análisis retrospectivos.
* **Gestión de Alarmas:** Monitorea los parámetros en tiempo real y dispara alertas si algo sale de los límites de seguridad definidos.

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