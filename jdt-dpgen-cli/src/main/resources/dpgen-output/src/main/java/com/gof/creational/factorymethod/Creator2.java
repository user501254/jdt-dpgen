package com.gof.creational.factorymethod;
public class Creator2 extends Creator {

	@Override
	protected Product factoryMethod() {
		return new ConcreteProduct2();
	}
}