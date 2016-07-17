package sla.nightraven;

public interface IModule extends AutoCloseable {

	public void update() throws Exception;
}
