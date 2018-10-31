package br.com.devgrid.percentagebreakd.test.simple;

import static br.com.devgrid.percentagebreakd.util.DateTimeUtil.createTimestamp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.com.devgrid.percentagebreakd.exec.BreakdownCalculatorExec;
import br.com.devgrid.percentagebreakd.model.Appliance;
import br.com.devgrid.percentagebreakd.model.SensorEvent;

/**
 * Teste com nenhum evento contendo consumo para a aplicação testada
 * 
 * @author Cesar Buzin
 */
public class SimpleTest6 {

	private ArrayList<SensorEvent> sensorEvents = new ArrayList<>();
	private BreakdownCalculatorExec breakdownCalculator = new BreakdownCalculatorExec();

	@Before
	public void setup() {
		sensorEvents.add(new SensorEvent(createTimestamp(0, 15), Arrays.asList(0, 0, 0, 0)));
		sensorEvents.add(new SensorEvent(createTimestamp(0, 20), Arrays.asList(1, 1, 1, 2)));
		sensorEvents.add(new SensorEvent(createTimestamp(0, 30), Arrays.asList(1, 1, 1, 2)));
		sensorEvents.add(new SensorEvent(createTimestamp(0, 40), Arrays.asList(1, 1, 1, 2)));
		sensorEvents.add(new SensorEvent(createTimestamp(0, 45), Arrays.asList(2, 3, 20, 2)));

		sensorEvents.add(new SensorEvent(createTimestamp(1, 15), Arrays.asList(3, 3, 30, 2)));
		sensorEvents.add(new SensorEvent(createTimestamp(1, 25), Arrays.asList(4, 4, 40, 2)));
		sensorEvents.add(new SensorEvent(createTimestamp(1, 35), Arrays.asList(6, 6, 60, 2)));

		sensorEvents.add(new SensorEvent(createTimestamp(2, 15), Arrays.asList(7, 9, 77, 2)));
		sensorEvents.add(new SensorEvent(createTimestamp(2, 30), Arrays.asList(9, 9, 99, 2)));
		sensorEvents.add(new SensorEvent(createTimestamp(2, 45), Arrays.asList(12, 12, 102, 1)));

		sensorEvents.add(new SensorEvent(createTimestamp(3, 15), Arrays.asList(13, 17, 104, 2)));
		sensorEvents.add(new SensorEvent(createTimestamp(3, 20), Arrays.asList(1, 1, 1, 2)));
		sensorEvents.add(new SensorEvent(createTimestamp(3, 25), Arrays.asList(15, 18, 107, 2)));
		sensorEvents.add(new SensorEvent(createTimestamp(3, 30), Arrays.asList(1, 1, 1, 2)));
		sensorEvents.add(new SensorEvent(createTimestamp(3, 35), Arrays.asList(20, 25, 220, 2)));

		sensorEvents.add(new SensorEvent(createTimestamp(4, 15), Arrays.asList(22, 28, 222, 2)));

		// SE É O CONSUMO TOTAL DIÁRIO, NÃO PODERIA SER INFERIOR AOS ANTERIORES
		// PORÉM, APLICAÇÃO TRATA PARA CASOS DE VARIAÇÃO DO SENSOR
		sensorEvents.add(new SensorEvent(createTimestamp(4, 30), Arrays.asList(20, 29, 230, 2)));
		sensorEvents.add(new SensorEvent(createTimestamp(4, 45), Arrays.asList(30, 30, 233, 2)));

		// Sensor detectou uma nova aplicação
		sensorEvents.add(new SensorEvent(createTimestamp(5, 15), Arrays.asList(-31, 32, 234, 2)));
		sensorEvents.add(new SensorEvent(createTimestamp(5, 25), Arrays.asList(-33, 33, 235, 3)));
		sensorEvents.add(new SensorEvent(createTimestamp(5, 35), Arrays.asList(42, 42, 242, 3)));

		sensorEvents.add(new SensorEvent(createTimestamp(6, 15), Arrays.asList(44, 50, 244, 3)));
		sensorEvents.add(new SensorEvent(createTimestamp(6, 30), Arrays.asList(46, 55, 245, 3)));
		sensorEvents.add(new SensorEvent(createTimestamp(6, 40), Arrays.asList(1, 1, 1, 2)));
		sensorEvents.add(new SensorEvent(createTimestamp(6, 45), Arrays.asList(56, 65, 256, 3)));

		sensorEvents.add(new SensorEvent(createTimestamp(7, 15), Arrays.asList(57, 70, 259, 3)));
		sensorEvents.add(new SensorEvent(createTimestamp(7, 25), Arrays.asList(60, -71, 266, 3)));
		sensorEvents.add(new SensorEvent(createTimestamp(7, 35), Arrays.asList(72, 82, 272, 3)));

		sensorEvents.add(new SensorEvent(createTimestamp(8, 15), Arrays.asList(73, 83, 274, 3)));
		sensorEvents.add(new SensorEvent(createTimestamp(8, 30), Arrays.asList(76, 85, 276, 3)));
		sensorEvents.add(new SensorEvent(createTimestamp(8, 35), Arrays.asList(78, 86, -278, 3)));
		sensorEvents.add(new SensorEvent(createTimestamp(8, 38), Arrays.asList(80, 87, 281, 3)));
		sensorEvents.add(new SensorEvent(createTimestamp(8, 48), Arrays.asList(90, 120, 290, 3)));
	}

	@Test
	public void testApplianceBreakdownSingleAppliance() throws Exception {
		Appliance appliance = new Appliance(5, "key 1", 100);
		Collection<Integer> calculate = breakdownCalculator.calculate(sensorEvents, appliance);
		Assert.assertEquals(Arrays.asList(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
				calculate);
	}
}
