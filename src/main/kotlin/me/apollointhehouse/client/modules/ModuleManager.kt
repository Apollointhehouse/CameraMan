package me.apollointhehouse.client.modules

object ModuleManager {
	private val modules = mutableListOf<Module>()

	fun register(module: Module) {
		modules.add(module)
	}

	fun getModules(): List<Module> {
		return modules
	}

	fun enableAll() {
		for (module in modules) {
			module.enable()
		}
	}

	fun disableAll() {
		for (module in modules) {
			module.disable()
		}
	}
}
