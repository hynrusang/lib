#define _CRT_SECURE_NO_WARNINGS
#define PROCESS

#if defined DATAS

#define type int
#include "1.0/datas.h"
int main() {
	const int count = 10; const char* const _Format = "%d\t";
	Stack* stack = newStack();
	if (!stack) return -1;
	stack->display(stack, _Format);
	for (int i = 0; i < count; i++) {
		stack->push(stack, i);
		stack->display(stack, _Format);
	}
	for (int i = 0; i < count; i++) {
		stack->pop(stack);
		stack->display(stack, _Format);
	}
	printf("\n");

	Queue* queue = newQueue();
	if (!queue) return -1;
	queue->display(queue, _Format);
	for (int i = 0; i < count; i++) {
		queue->enqueue(queue, i);
		queue->display(queue, _Format);
	}
	for (int i = 0; i < count; i++) {
		queue->dequeue(queue);
		queue->display(queue, _Format);
	}
	printf("\n");

	Deque* deque = newDeque();
	if (!deque) return -1;
	deque->display(deque, _Format);
	for (int i = 0; i < count / 2; i++) {
		deque->push(deque, i);
		deque->display(deque, _Format);
	}
	for (int i = count / 2; i < count; i++) {
		deque->pushLeft(deque, i);
		deque->display(deque, _Format);
	}
	for (int i = 0; i < count / 2; i++) {
		deque->pop(deque);
		deque->display(deque, _Format);
	}
	for (int i = count / 2; i < count; i++) {
		deque->popLeft(deque);
		deque->display(deque, _Format);
	}
	printf("\n");

	List* list = newList();
	if (!list) return -1;
	list->display(list, _Format);
	for (int i = 0; i < count; i++) {
		list->insert(list, i, i % 5);
		list->display(list, _Format);
	}
	for (int i = 0; i < count; i++) {
		list->remove(list, i % 5);
		list->display(list, _Format);
	}
	printf("\n");
	return 0;
}

#elif defined DBREADER

#define DB struct db
DB {
	char name[30];
	int id;
	int data;
};
#include <string.h>
#include "1.0/dbreader.h"

bool filter(DB* db, char* name) {
	return !strcmp(db->name, name);
}
int main() {
	DB* db = malloc(sizeof(DB));
	DBReader* reader = newDBReader("database.db");
	if (!db || !reader) return -1;
	printf("length:\t%d\n", reader->length);
	if (!reader->length) {
		for (int i = 0; i < 10; i++) {
			strcpy(db->name, "undefined");
			db->id = db->data = i;
			reader->write(reader, db);
		}
	}
	printf("length:\t%d\n", reader->length);
	if (reader->read(reader, db, reader->find(reader, "hynrusang", filter))) printf("find:\t%s\t%d\t%d\n\n", db->name, db->id, db->data);
	db->id = 10;
	strcpy(db->name, "hynrusang");
	db->data = 39;
	reader->rewrite(reader, db, db->id);
	printf("length:\t%d\n", reader->length);
	for (int i = 0; i <= reader->length; i++) if (reader->read(reader, db, i)) printf("%d:\t%s      \t%d\t%d\n", i, db->name, db->id, db->data);
	return 0;
}

#elif defined LIVEDATA

#define livetype int
#include "1.0/livedata.h"
#include <time.h>
#include <stdlib.h>

void observer(LiveData* livedata) {
	printf("roll was change: %d\n", livedata->get(livedata));
}
int main() {
	srand(time(NULL)); int count = 0; const int attemptLimit = 6;
	LiveData* owner = newLiveData(rand() % attemptLimit + 1, observer);
	LiveData* user = newLiveData(rand() % attemptLimit + 1, observer);
	if (!owner || !user) return -1;
	printf("(owner, user) = (%d, %d)\n", owner->get(owner), user->get(user));
	while (owner->get(owner) != user->get(user)) {
		if (user->set(user, rand() % attemptLimit + 1)) count++;
		if (attemptLimit < count) {
			printf("\nuser was lose... (owner, user) = (%d, %d);\ntry attempt: %d\n", owner->get(owner), user->get(user), count);
			return 0;
		}
	}
	printf("\nowner and user's roll was same at (%d, %d);\ntry attempt: %d\n", owner->get(owner), user->get(user), count);
	return 0;
}

#elif defined PROCESS

#include "1.0/process.h"

#endif