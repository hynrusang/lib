#ifndef FSTRUCTURE_H_
#define FSTRUCTURE_H_

#include "ObjectView.h"

class Launcher;
class Interface {
public:
	Interface() { };
	virtual ~Interface() = default;
};

class Initializer {

public:
	Initializer() { };
	virtual ~Initializer() = default;

	void init() { onInit() ? onInitSuccess() : onInitFailed(); }

protected:
	virtual bool onInit() noexcept = 0;
	virtual void onInitSuccess() noexcept = 0;
	virtual void onInitFailed() noexcept = 0;

};

class Routine {

public:
	Routine(Launcher* launcher) : mLauncher(launcher) { };
	virtual ~Routine() = default;

	Launcher* getLauncher() { return mLauncher; }

	virtual bool isReady() noexcept = 0;
	virtual void execute() noexcept = 0;

private:
	Launcher* mLauncher;

};

class Launcher {

public:
	Launcher() { };
	virtual ~Launcher() = default;

	void launch() noexcept {
		auto routines = getRoutines();
		while (true) {
			for (std::size_t i = 0; i < routines.size; ++i) {
				if (routines.objects[i]->isReady()) {
					routines.objects[i]->execute();
				}
			}
		}
	}

	virtual Interface* getInterface() noexcept = 0;

protected:
	virtual ObjectView<Routine*> getRoutines() noexcept = 0;

};

#endif /* FSTRUCTURE_H_ */
