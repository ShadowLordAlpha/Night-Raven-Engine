package sla.nightraven.util.function;

@FunctionalInterface
public interface ObjBoolConsumer<T> {

	void accept(T t, boolean value);
}
