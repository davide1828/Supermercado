package clases;

import java.util.ArrayList;
import java.util.List;

// Clase que representa a un cajero en el supermercado
class Cajero extends Thread {

    private int id;                 // Identificador único del cajero
    private String nombre;          // Nombre del cajero
    private List<Cliente> colaClientes; // Cola de clientes esperando ser atendidos por el cajero
    private long tiempoTotal;       // Tiempo total de atención acumulado por el cajero
    private Thread hilo;            // Hilo de ejecución del cajero
    private static List<Cajero> cajeras = new ArrayList<>(); // Lista estática de cajeros en el supermercado

    // Constructor que inicializa las propiedades del cajero
    public Cajero(String nombre) {
        this.nombre = nombre;
        colaClientes = new ArrayList<>();
        getCajeras().add(this);     // Se agrega el cajero a la lista de cajeros en el supermercado
        this.id = getCajeras().size() - 1; // Se asigna el ID basado en la posición en la lista
    }

    // Método que maneja el cobro de un cliente por parte del cajero
    public void clienteCobrado(long tiempoEspera) {
        // Implementa la lógica para manejar el tiempo de espera del cliente
        System.out.println("Cliente cobrado por Cajero " + id + ", tiempo de espera: " + tiempoEspera + " ms");
    }

    // Métodos de acceso a las propiedades del cajero
    public long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public List<Cliente> getColaClientes() {
        return colaClientes;
    }

    // Métodos estáticos de acceso a la lista de cajeros en el supermercado
    public static List<Cajero> getCajeras() {
        return cajeras;
    }

    public static void setCajeras(List<Cajero> cajeras) {
        Cajero.cajeras = cajeras;
    }

    //  Método run() que se ejecuta cuando se inicia el hilo del cajero
    @Override
    public void run() {
        // Implementa la lógica para atender a los clientes en la cola (puedes agregar tu lógica aquí)
    }
}
