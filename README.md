# Guía de Inicio para usar YAMCS 🛰️

Bienvenido al repositorio central de aprendizaje de **YAMCS**. Este proyecto tiene como objetivo centralizar el conocimiento necesario para gestionar la telemetría y telecomandos de satélites.

## ¿Qué es YAMCS?
Es una plataforma de software libre diseñada para el control y monitorización de misiones espaciales. A diferencia de un simple panel de control, YAMCS es el cerebro intermedio: recibe los datos en bruto de la antena, los procesa, los archiva en una base de datos histórica y permite enviar comandos de vuelta al satélite.

## ¿Para qué sirve?

1. Telemetría: Traducir ceros y unos en gráficas y tablas comprensibles.
2. Telecomandos: Enviar órdenes seguras al satélite.
3. Archivo: Guardar cada bit recibido para análisis posterior.
4. Automatización: Permitir que scripts (Python) vigilen el satélite por nosotros.

---

# 📂 Estructura de este Repositorio
Este repositorio utiliza "ramas huérfanas" para separar los diferentes entornos de trabajo:
* [`main`](https://github.com/albertojroma/YAMCS-UPV/tree/main): 📖 Documentación y guías en español (donde estás ahora).
* [`myproject`](https://github.com/albertojroma/YAMCS-UPV/tree/myproject): 🚀 Proyecto base oficial de YAMCS (punto de partida limpio).
* [`yamcs-training`](https://github.com/albertojroma/YAMCS-UPV/tree/training): 🎓 Rama dedicada a explicar la creación de satélites/misiones en YAMCS basado en material del proyecto AcubeSAT
* [`yamcs-workshop`](https://github.com/albertojroma/YAMCS-UPV/tree/workshop): 🛠️ Entorno de taller basado en material del proyecto AcubeSAT

---

# 🗺️ Hoja de Ruta de Aprendizaje (*Roadmap*)

Para dominar YAMCS se recomienda seguir el siguiente orden:

## 1. Fundamentos Teóricos
Antes de instalar nada, es vital entender qué vamos a usar y con qué objetivo:
* [Conceptos Básicos de YAMCS](docs/01-conceptos.md)

## 2. Preparación del Entorno
En este [enlace](https://gitlab.com/acubesat/education/yamcs-workshop/-/blob/main/Prerequisites.md?ref_type=heads) se explican los requisitos y el procedimiento de instalación (Windows/Linux/macOS).

## 3. Primeros pasos
El [tutorial oficial](https://yamcs.org/getting-started) es la manera más fácil de comenzar.

## 4. Entrenamiento Práctico (Training)
La rama [`yamcs-training`](https://github.com/albertojroma/YAMCS-UPV/tree/training) contiene nociones básicas para poder crear una misión en YAMCS.

