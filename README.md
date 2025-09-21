<h1>Sistema de Paquetería</h1>

<p style="text-align: justify;">El Sistema de Paquetería desarrollado por el Equipo 4 es una aplicación en Java con interfaz gráfica construida en Swing y base de datos MySQL. 
El sistema tiene como propósito administrar de manera integral los procesos de una empresa de paquetería, desde el registro de clientes hasta el control de envíos, paquetes, empleados y reportes de operación. El sistema cuenta con un inicio de sesión que permite a los usuarios ingresar con distintos roles. Cada rol tiene un panel personalizado con opciones adaptadas a sus funciones.
Por ejemplo, el cajero puede registrar clientes y crear envíos, el supervisor gestiona empleados, tiendas y zonas además de generar reportes, el repartidor consulta los envíos asignados y actualiza las entregas, mientras que el almacenista organiza los paquetes dentro del almacén. De esta manera, cada usuario tiene acceso solo a lo que necesita segúnsu perfil.</p>
En el módulo de clientes, es posible registrar nuevos clientes con datos como nombre, apellidos, teléfono y correo electrónico, validando que no se repitan. 
El sistema genera automáticamente un código único para cada cliente (por ejemplo, CLI001, CLI002, etc.) y permite registrar múltiples direcciones asociadas a él,ya sea residencial o de envío. También se pueden editar, desactivar o eliminar clientes.
El módulo de envíos permite crear un nuevo envío con toda la información necesaria: número de seguimiento, remitente, destinatario, direcciones de origen y destino,tipo de envío (Normal, Express, Económico), prioridad (Alta, Media, Baja) y observaciones adicionales. 
El sistema calcula de manera automática el costo total, tomando en cuenta peso de los paquetes y descuentos aplicados.
Cada envío puede estar compuesto por uno o varios paquetes, los cuales se registran con su descripción, peso, tipo de contenido, fragilidad y valor declarado. 

<p style="text-align: justify;">El envío pasa por distintos estados que permiten su seguimiento, como Registrado, En proceso, En tránsito, En reparto, Entregado, Devuelto o Cancelado.
Los empleados también forman parte importante del sistema. Se administran sus datos personales, número de empleado, puesto, salario y tienda a la que pertenecen.
Los supervisores pueden asignar vehículos y zonas de reparto a los repartidores, además de llevar un control de su rendimiento.  De igual forma, las tiendas cuentan con datos de ubicación, horarios, gerente y capacidad de almacén, mientras que las zonas de reparto se asocian a una tienda y se asignan a repartidores activos.</p> <br>

<ol>
<li>Una de las funcionalidades más útiles para los supervisores es la generación de reportes. El sistema permite obtener estadísticas de ingresos por periodo, identificar a los clientes más frecuentes y con mayor gasto, evaluar el rendimiento de repartidores (envíos realizados, entregas exitosas y calificación promedio), así como un resumen de los estados de los envíos. Esto brinda información valiosa para la toma de decisiones y la mejora continua de la empresa.</li>

<li>El proyecto se desarrolló con Java, empleando Swing para la interfaz gráfica y JDBC para la conexión con MySQL. La organización del código sigue un modelo basado en controladores (DAO), modelos de datos y vistas gráficas, lo que facilita su mantenimiento y escalabilidad.</li>
</ol>

