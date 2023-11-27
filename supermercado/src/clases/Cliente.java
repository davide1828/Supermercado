package clases;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Cliente {

    private int id;               // Identificador único del cliente
    private List<Producto> productos; // Lista de productos que el cliente va a comprar
    private Cajero cajero;        // Cajero asignado para cobrar al cliente
    private List<Cajero> cajeras; // Lista de cajeros disponibles (se inicializa con un cajero)
    private long tiempoCreacion;  // Tiempo en milisegundos en el que se creó el cliente
    private long tiempoEspera;    // Tiempo de espera del cliente para ser cobrado

    // Constructor que inicializa las propiedades del cliente
    public Cliente(int id) {
        this.id = id;
        this.productos = new ArrayList<>();
        this.cajeras = new ArrayList<>();
        this.cajeras.add(new Cajero("Cajero 0")); // Se agrega un cajero a la lista de cajeros
        this.tiempoCreacion = System.currentTimeMillis();

        // Generar productos aleatorios (10 productos)
        for (int i = 0; i < 10; i++) {
            Producto producto = new Producto("Producto " + i, (i + 1) * 10); // Precio incrementado por 10
            agregarProducto(producto);
        }
    }

    // Método para agregar un producto a la lista de productos del cliente
    public void agregarProducto(Producto producto) {
        productos.add(producto);
    }

    // Método principal para el proceso de cobro del cliente
    public void cobrar() {
        int idCajero = new Random().nextInt(cajeras.size()); // Se elige un cajero aleatorio
        cajero = cajeras.get(idCajero);

        System.out.println("Cliente " + id + ", Cajero asignado: " + cajero.getId());

        // Procesar cada producto del cliente
        for (Producto producto : productos) {
            System.out.println("Cliente " + id + ", procesando producto " + producto.getNombre() +
                    ", costo: " + producto.getPrecio());

            try {
                Thread.sleep((long) (producto.getPrecio() / 100)); // Simular el tiempo de procesamiento por producto
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Informar al cajero que el cliente ha sido cobrado y calcular el tiempo de espera
        tiempoEspera = System.currentTimeMillis() - tiempoCreacion;
        cajero.clienteCobrado(tiempoEspera);
    }

    // Métodos de acceso a las propiedades del cliente
    public int getId() {
        return id;
    }

    public List<Producto> getProductos() {
        return productos;
    }

    public Cajero getCajero() {
        return cajero;
    }

    public long getTiempoEspera() {
        return tiempoEspera;
    }

    public long getTiempoLlegada() {
        return tiempoCreacion;
    }

    public void setTiempoEspera(long tiempoEspera) {
        this.tiempoEspera = tiempoEspera;
    }
}
