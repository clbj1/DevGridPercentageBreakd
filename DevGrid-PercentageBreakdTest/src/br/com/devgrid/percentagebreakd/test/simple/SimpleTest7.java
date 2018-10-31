package br.com.devgrid.percentagebreakd.test.simple;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.junit.Assert;
import org.junit.Test;

import br.com.devgrid.percentagebreakd.exec.BreakdownCalculatorExec;
import br.com.devgrid.percentagebreakd.model.Appliance;
import br.com.devgrid.percentagebreakd.model.SensorEvent;

/**
 * Teste sem nenhum evento informado
 * 
 * @author Cesar Buzin
 */
public class SimpleTest7 {

	private ArrayList<SensorEvent> sensorEvents = new ArrayList<>();
	private BreakdownCalculatorExec breakdownCalculator = new BreakdownCalculatorExec();

	@Test
	public void testApplianceBreakdownSingleAppliance() throws Exception {
		Appliance appliance = new Appliance(5, "key 1", 100);
		Collection<Integer> calculate = breakdownCalculator.calculate(sensorEvents, appliance);
		Assert.assertEquals(Arrays.asList(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
				calculate);
	}
}
