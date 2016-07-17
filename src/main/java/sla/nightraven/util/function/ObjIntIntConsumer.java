package sla.nightraven.util.function;

@FunctionalInterface
public interface ObjIntIntConsumer<T> {

	void accept(T t, int value, int value2);
}
