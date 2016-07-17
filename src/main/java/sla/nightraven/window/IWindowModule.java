package sla.nightraven.window;

import sla.nightraven.IModule;

public interface IWindowModule extends IModule {

	IWindowBuilder newBuilder(); // TODO: this may need to be in IModule

}
