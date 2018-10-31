package br.com.devgrid.percentagebreakd.test;

import static br.com.devgrid.percentagebreakd.util.DateTimeUtil.createTimestamp;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.com.devgrid.percentagebreakd.exec.BreakdownCalculatorExec;
import br.com.devgrid.percentagebreakd.model.Appliance;
import br.com.devgrid.percentagebreakd.model.SensorEvent;

/**
 * Classe dinâmica criada para geração automática de casos de testes e execução
 * dos mesmos.
 * 
 * @author Cesar Buzin
 */
public class DynamicPerformTest {

	/**
	 * Random par auxiliar na geração automática de casos de testes
	 */
	private final Random random = new Random();

	/**
	 * Número de aplicações, horas, minutos e se deve gerar consumos negativos ou
	 * nulos no meio da aplicação (Informações geradas dinamicamente)
	 */
	private final int NUM_APPS = random.nextInt(10);
	private final int NUM_HORAS = random.nextInt(24);
	private final int NUM_MINUTOS = random.nextInt(60);
	private final boolean RANDOM_NULL = random.nextBoolean();
	private final boolean RANDOM_NEGATIVE = random.nextBoolean();

	/**
	 * Lista de dados do sensor
	 */
	private ArrayList<SensorEvent> sensorEvents = new ArrayList<>();

	/**
	 * Exec que realiza cálculos
	 */
	private BreakdownCalculatorExec breakdownCalculator = new BreakdownCalculatorExec();

	@Before
	public void setup() {

		// Contador para consumo
		int power = 1;

		// Iteração sobre número de horas informado
		for (int i = 0; i < NUM_HORAS; i++) {

			// Iteração sobre número de minutos informado
			for (int j = 0; j < NUM_MINUTOS; j++) {

				// Iteração sobre número de aplicações informadas
				List<Integer> lstApps = new ArrayList<Integer>();
				for (int k = 0; k < NUM_APPS; k++) {

					// Caso seja o último minuto da hora, mantém o valor de consumo (para não
					// estragar a verificação)
					if (j == NUM_MINUTOS - 1) {
						lstApps.add(power);
						continue;
					}

					/*
					 *  Gera um randômico, dependendo das configurações randômicas do início do teste, que pode ser:
					 *  - Gerar consumo normal
					 *  - Gerar consumo negativo
					 *  - Gerar consumo nulo
					 */
					
					int rand = random.nextInt(3);

					if (RANDOM_NEGATIVE && rand == 1)
						lstApps.add(-1);
					else if (RANDOM_NULL && rand == 2)
						lstApps.add(null);
					else
						lstApps.add(power);
				}
				
				// Cria o evento do sensor, exibindo-o em tela
				SensorEvent se = new SensorEvent(createTimestamp(i, j), lstApps);
				System.out.println(se);
				sensorEvents.add(se);
				power++;
			}
		}
	}

	@Test
	public void testApplianceBreakdownSingleAppliance() throws Exception {

		// Realiza a execução para cada aplicação, exibindo em tela o tempo de execução e ao final, uma média dos tempos
		// TODO melhoria para validar tempos ao invés de exibí-los
		
		Long sumTimes = 0l;
		for (int i = 1; i <= NUM_APPS; i++) {

			Appliance appliance = new Appliance(i, "key " + i, NUM_MINUTOS * NUM_HORAS);
			Long time = new Date().getTime();
			Collection<Integer> calculate = breakdownCalculator.calculate(sensorEvents, appliance);
			time = new Date().getTime() - time;
			System.out.println("Tempo de execução [" + appliance.getName() + "]: " + time + "ms");
			sumTimes += time;

			List<Integer> lstResult = new ArrayList<Integer>();
			for (int j = 0; j < NUM_HORAS; j++) {
				lstResult.add(100 / NUM_HORAS);
			}
			for (int j = NUM_HORAS; j < 24; j++) {
				lstResult.add(0);
			}

			Assert.assertEquals(lstResult, calculate);
		}

		System.out.println("Tempo médio de execução: " + (sumTimes / NUM_APPS) + "ms");
	}
}
