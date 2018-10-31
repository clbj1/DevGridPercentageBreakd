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
 * Teste com consumo zerado para o ID da aplicação em alguns eventos retornados
 * 
 * @author Cesar Buzin
 */
public class SimpleTest5 {

	private ArrayList<SensorEvent> sensorEvents = new ArrayList<>();
	private BreakdownCalculatorExec breakdownCalculator = new BreakdownCalculatorExec();

	@Before
	public void setup() {
		sensorEvents.add(new SensorEvent(createTimestamp(0, 15), Arrays.asList(0, 0, 0, 0, 0)));
		sensorEvents.add(new SensorEvent(createTimestamp(0, 20), Arrays.asList(1, 1, 1, 2, 0)));
		sensorEvents.add(new SensorEvent(createTimestamp(0, 30), Arrays.asList(1, 1, 1, 2, 1)));
		sensorEvents.add(new SensorEvent(createTimestamp(0, 40), Arrays.asList(1, 1, 1, 2, 0)));
		sensorEvents.add(new SensorEvent(createTimestamp(0, 45), Arrays.asList(2, 3, 20, 2, 2)));

		sensorEvents.add(new SensorEvent(createTimestamp(1, 15), Arrays.asList(3, 3, 30, 2, 3)));
		sensorEvents.add(new SensorEvent(createTimestamp(1, 25), Arrays.asList(4, 4, 40, 2, 4)));
		sensorEvents.add(new SensorEvent(createTimestamp(1, 35), Arrays.asList(6, 6, 60, 2, 6)));

		sensorEvents.add(new SensorEvent(createTimestamp(2, 15), Arrays.asList(7, 9, 77, 2, 7)));
		sensorEvents.add(new SensorEvent(createTimestamp(2, 30), Arrays.asList(9, 9, 99, 2, 9)));
		sensorEvents.add(new SensorEvent(createTimestamp(2, 45), Arrays.asList(12, 12, 102, 1, 12)));

		sensorEvents.add(new SensorEvent(createTimestamp(3, 15), Arrays.asList(13, 17, 104, 2, 13)));
		sensorEvents.add(new SensorEvent(createTimestamp(3, 20), Arrays.asList(1, 1, 1, 2, 0)));
		sensorEvents.add(new SensorEvent(createTimestamp(3, 25), Arrays.asList(15, 18, 107, 2, 15)));
		sensorEvents.add(new SensorEvent(createTimestamp(3, 30), Arrays.asList(1, 1, 1, 2, 0)));
		sensorEvents.add(new SensorEvent(createTimestamp(3, 35), Arrays.asList(20, 25, 220, 2, 20)));

		sensorEvents.add(new SensorEvent(createTimestamp(4, 15), Arrays.asList(22, 28, 222, 2, 22)));

		// SE É O CONSUMO TOTAL DIÁRIO, NÃO PODERIA SER INFERIOR AOS ANTERIORES
		// PORÉM, APLICAÇÃO TRATA PARA CASOS DE VARIAÇÃO DO SENSOR
		sensorEvents.add(new SensorEvent(createTimestamp(4, 30), Arrays.asList(20, 29, 230, 2, 20)));
		sensorEvents.add(new SensorEvent(createTimestamp(4, 45), Arrays.asList(30, 30, 233, 2, 30)));

		// Sensor detectou uma nova aplicação
		sensorEvents.add(new SensorEvent(createTimestamp(5, 15), Arrays.asList(-31, 32, 234, 2, -31, 0)));
		sensorEvents.add(new SensorEvent(createTimestamp(5, 25), Arrays.asList(-33, 33, 235, 3, -33, 1)));
		sensorEvents.add(new SensorEvent(createTimestamp(5, 35), Arrays.asList(42, 42, 242, 3, 42, 1)));

		sensorEvents.add(new SensorEvent(createTimestamp(6, 15), Arrays.asList(44, 50, 244, 3, 44, 34)));
		sensorEvents.add(new SensorEvent(createTimestamp(6, 30), Arrays.asList(46, 55, 245, 3, 46, 35)));
		sensorEvents.add(new SensorEvent(createTimestamp(6, 40), Arrays.asList(1, 1, 1, 2, 0)));
		sensorEvents.add(new SensorEvent(createTimestamp(6, 45), Arrays.asList(56, 65, 256, 3, 56, 36)));

		sensorEvents.add(new SensorEvent(createTimestamp(7, 15), Arrays.asList(57, 70, 259, 3, 57, 40)));
		sensorEvents.add(new SensorEvent(createTimestamp(7, 25), Arrays.asList(60, -71, 266, 3, 60, 41)));
		sensorEvents.add(new SensorEvent(createTimestamp(7, 35), Arrays.asList(72, 82, 272, 3, 72, 45)));

		sensorEvents.add(new SensorEvent(createTimestamp(8, 15), Arrays.asList(73, 83, 274, 3, 73, 45)));
		sensorEvents.add(new SensorEvent(createTimestamp(8, 30), Arrays.asList(76, 85, 276, 3, 76, 45)));
		sensorEvents.add(new SensorEvent(createTimestamp(8, 35), Arrays.asList(78, 86, -278, 3, 78, 45)));
		sensorEvents.add(new SensorEvent(createTimestamp(8, 38), Arrays.asList(80, 87, 281, 3, 80, 45)));
		sensorEvents.add(new SensorEvent(createTimestamp(8, 48), Arrays.asList(90, 120, 290, 3, 90, 45)));
	}

	@Test
	public void testApplianceBreakdownSingleAppliance() throws Exception {
		Appliance appliance = new Appliance(5, "key 1", 100);
		Collection<Integer> calculate = breakdownCalculator.calculate(sensorEvents, appliance);
		Assert.assertEquals(Arrays.asList(2, 4, 6, 8, 10, 12, 14, 16, 18, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
				calculate);
	}
}
