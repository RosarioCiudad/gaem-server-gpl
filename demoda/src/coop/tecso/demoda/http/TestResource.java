package coop.tecso.demoda.http;

import java.util.Date;

import coop.tecso.demoda.util.MapTo;
import coop.tecso.demoda.util.To;

public class TestResource {
	To to = new To();

	/** 
	 * Input structure 
	 */ 
	static final class TestInput {
		public TestInput(Long id, String name, Double value, Date bith) {
			super();
			this.id = id;
			this.name = name;
			this.value = value;
			this.bith = bith;
		}
		final Long id;
		final String name;
		final Double value;
		final Date bith;
	}

	/** 
	 * Output structure 
	 */ 
	static final class TestOut {
		public TestOut(String msg, TestInput input, TestInput inputs[]) {
			super();
			this.msg = msg;
			this.input = input;
			this.inputs = inputs;
		}
		
		final String msg;
		final TestInput input;
		final TestInput inputs[];
	}
	
	/**
	 * For use from web request
	 */
	public Reply<TestOut> test1(RestRequest<String> req) throws Exception {		
		TestInput in = MapTo
				.from(req.parameters)
				.Long("id")
				.String("name")
				.Double("value") 
				.DateEpoch("bith")
				.construct(TestInput.class);

		return new Reply<TestOut>(test1(in));
	}

	/**
	 * Pure logic test1. For use internally
	 */
	public TestOut test1(TestInput in) throws Exception {
		String msg = String.format("Hello, %d : %s - birth : %s", in.id, in.name, in.bith);
		
		TestOut out = new TestOut(
				msg, 
				in, 
				new TestInput[] { in, in, in } );		
		
		return out;
	}

}
