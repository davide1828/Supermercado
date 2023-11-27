package clases;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SimulacionCobroSupermercados {
	private List<Cajero> cajeras;

	// Método principal
	public static void main(String[] args) throws InterruptedException {
		// Crear una instancia de SimulacionCobroSupermercados con 5 cajeras
		SimulacionCobroSupermercados simulacion = new SimulacionCobroSupermercados(5);

		// Ejecutar la simulación y obtener la lista de clientes
		List<Cliente> clientes = simulacion.ejecutar();

		// Inicializar un mapa para almacenar tiempos de espera por cajero
		Map<String, Long> tiemposEsperaPorCajero = new HashMap<>();

		// Inicializar variable para calcular el tiempo total de transacciones
		long tiempoTotalTransacciones = 0;

		// Calcular tiempos de espera por cajero y tiempo total de transacciones
		for (Cliente cliente : clientes) {
			Cajero cajero = cliente.getCajero();
			String idCajero = String.valueOf(cajero.getId());
			tiemposEsperaPorCajero.merge(idCajero, cliente.getTiempoEspera(), Long::sum);
			tiempoTotalTransacciones += cliente.getTiempoEspera();
		}

		// Calcular y mostrar el tiempo de espera promedio por cajero
		tiemposEsperaPorCajero.forEach((idCajero, tiempoEspera) -> {
			double tiempoEsperaPromedio = 0.0;
			long count = clientes.stream()
					.filter(cliente -> cliente.getCajero() != null && idCajero.equals(cliente.getCajero().getId()))
					.count();

			if (count > 0) {
				tiempoEsperaPromedio = tiempoEspera / count;
			}

			System.out.println(
					"Tiempo de espera promedio cajero " + idCajero + ": " + tiempoEsperaPromedio + " milisegundos");
		});

		// Mostrar el tiempo total de transacciones
		System.out.println("Tiempo total de transacciones: " + tiempoTotalTransacciones + " milisegundos");
	}

	// Constructor que inicializa la lista de cajeras
	public SimulacionCobroSupermercados(int numeroCajeras) {
		this.cajeras = new ArrayList<>();
		for (int i = 0; i < numeroCajeras; i++) {
			cajeras.add(new Cajero("Cajero " + i));
		}
	}

	// Método para ejecutar la simulación
	public List<Cliente> ejecutar() throws InterruptedException {
		// Inicializar listas para almacenar hilos y clientes
		List<Thread> threads = new ArrayList<>();
		List<Cliente> clientes = new ArrayList<>();

		// Crear 10 clientes y agregarlos a la lista
		for (int i = 0; i < 10; i++) {
			Cliente cliente = new Cliente(i);

			// Agregar productos con precios aleatorios a cada cliente
			cliente.agregarProducto(new Producto("Producto " + i, (i + 1) * 10));
			clientes.add(cliente);

			// Crear un hilo para cada cliente
			Thread thread = new Thread(() -> {
				// Proceso de cobro del cliente
				cliente.cobrar();

				// Mostrar información sobre el cliente cobrado y el hilo actual
				System.out.println("Cliente " + cliente.getId() + " cobrado por " + cliente.getCajero().getName()
						+ " en el hilo " + Thread.currentThread().getName());
			});

			// Agregar el hilo a la lista de hilos y comenzar la ejecución
			threads.add(thread);
			thread.start();
		}

		// Esperar a que todos los hilos terminen
		for (Thread thread : threads) {
			thread.join();
		}

		// Devolver la lista de clientes
		return clientes;
	}
}

