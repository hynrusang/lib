/*
To use this header,
you must use a #define type *
before including this header file.
*/

#if defined type && !defined DATASHEADER

#define DATASHEADER
#include "init.h"
typedef struct element Element;
typedef struct stack Stack;
typedef struct queue Queue;
typedef struct deque Deque;
typedef struct list List;

struct element {
	type data;
	Element* before;
	Element* next;
};
struct stack {
	Element* _First;
	Element* _Last;

	// return datastruct is empty.
	bool (*isEmpty)(void* this);
	// print all Element of datastruct.
	void (*display)(void* this, const char* const _Format);
	// clear all Element of datastruct.
	void (*clear)(void* this);
	// push data to datastruct's last.
	void (*push)(void* this, type data);
	// pop data to datastruct's last. if datastruct is empty, return -1.
	type (*pop)(void* this);
	// get last data of datastruct. if datastruct is empty, return -1.
	type (*top)(void* this);
};
struct queue {
	Element* _First;
	Element* _Last;

	// return datastruct is empty.
	bool (*isEmpty)(void* this);
	// print all Element of datastruct.
	void (*display)(void* this, const char* const _Format);
	// clear all Element of datastruct.
	void (*clear)(void* this);
	// push data to datastruct's last.
	void (*enqueue)(void* this, type data);
	// pop data to datastruct's first. if datastruct is empty, return -1.
	type (*dequeue)(void* this);
	// get first data of datastruct. if datastruct is empty, return -1.
	type (*bottom)(void* this);
};
struct deque {
	Element* _First;
	Element* _Last;

	// return datastruct is empty.
	bool (*isEmpty)(void* this);
	// print all Element of datastruct.
	void (*display)(void* this, const char* const _Format);
	// clear all Element of datastruct.
	void (*clear)(void* this);
	// push data to datastruct's last.
	void (*push)(void* this, type data);
	// push data to datastruct's first.
	void (*pushLeft)(void* this, type data);
	// pop data to datastruct's last. if datastruct is empty, return -1.
	type (*pop)(void* this);
	// pop data to datastruct's first. if datastruct is empty, return -1.
	type (*popLeft)(void* this);
	// get last data of datastruct. if datastruct is empty, return -1.
	type (*top)(void* this);
	// get first data of datastruct. if datastruct is empty, return -1.
	type (*bottom)(void* this);
};
struct list {
	Element* _First;
	Element* _Last;

	// return datastruct is empty.
	bool (*isEmpty)(void* this);
	// get data's index in list. if data was not exist in list, return -2.
	int (*find)(void* this, type data);
	// print all Element of datastruct.
	void (*display)(void* this, const char* const _Format);
	// clear all Element of datastruct.
	void (*clear)(void* this);
	// push data to datastruct's pos. if pos is -1, push data to datastruct's last
	void (*insert)(void* this, type data, int pos);
	// pop data to datastruct's pos. if pos is -1, pop data to datastruct's last.
	type (*remove)(void* this, int pos);
	// get last data of datastruct. if datastruct is empty, return -1.
	type (*top)(void* this);
	// get first data of datastruct. if datastruct is empty, return -1.
	type (*bottom)(void* this);
};

bool Stack$isEmpty(Stack* this) {
	return !this->_First;
}
void Stack$display(Stack* this, const char* const _Format) {
	if (this->_First) {
		Element* iterator = this->_First;
		while (iterator) {
			printf(_Format, iterator->data);
			iterator = iterator->next;
		}
		printf("\n");
	} else printf("data struct:%p is Empty\n", this);
}
void Stack$clear(Stack* this) {
	if (this->_First) {
		while (this->_First != this->_Last) {
			this->_Last = this->_Last->before;
			free(this->_Last->next);
		}
		this->_First = this->_Last = NULL;
	}
}
void Stack$push(Stack* this, type data) {
	Element* temp = _create(Element);
	if (temp) {
		temp->data = data;
		temp->next = NULL;
		if (!this->_First) {
			this->_First = this->_Last = temp;
			temp->before = NULL;
		} else {
			temp->before = this->_Last;
			this->_Last->next = temp;
			this->_Last = temp;
		}
	} else _memory_is_full;
}
type Stack$pop(Stack* this) {
	if (this->_First) {
		type temp = this->_Last->data;
		if (this->_First == this->_Last) {
			free(this->_First);
			this->_First = this->_Last = NULL;
		} else {
			this->_Last = this->_Last->before;
			free(this->_Last->next);
			this->_Last->next = NULL;
		}
		return temp;
	}
	return -1;
}
type Stack$top(Stack* this) {
	return this->_First ? this->_Last->data : -1;
}

type Queue$dequeue(Queue* this) {
	if (this->_First) {
		type temp = this->_First->data;
		if (this->_First == this->_Last) {
			free(this->_First);
			this->_First = this->_Last = NULL;
		} else {
			this->_First = this->_First->next;
			free(this->_First->before);
			this->_First->before = NULL;
		}
		return temp;
	}
	return -1;
}
type Queue$bottom(Queue* this) {
	return this->_First ? this->_First->data : -1;
}

void Deque$pushLeft(Deque* this, type data) {
	Element* temp = _create(Element);
	if (temp) {
		temp->data = data;
		temp->next = NULL;
		if (!this->_First) {
			this->_First = this->_Last = temp;
			temp->before = NULL;
		} else {
			temp->next = this->_First;
			this->_First->before = temp;
			this->_First = temp;
		}
	} else _memory_is_full;
}

void List$insert(List* this, type data, int pos) {
	Element* temp = _create(Element);
	Element* iterator = (0 <= pos) ? this->_First : NULL;
	if (temp) {
		temp->data = data;
		if (!this->_First) {
			this->_First = this->_Last = temp;
			temp->before = temp->next = NULL;
		} else {
			for (int i = 0; i < pos && iterator != NULL; i++) iterator = iterator->next;
			temp->before = iterator ? iterator->before : this->_Last;
			temp->next = iterator;
			if (iterator == this->_First) {
				iterator->before = temp;
				this->_First = temp;
			} else if (iterator == NULL) {
				this->_Last->next = temp;
				this->_Last = temp;
			} else {
				iterator->before->next = temp;
				iterator->before = temp;
			}
		}
	} else _memory_is_full;
}
type List$remove(List* this, int pos) {
	if (pos < -1 || !this->_First) return -1;
	else {
		Element* iterator = (0 <= pos) ? this->_First : this->_Last;
		for (int i = 0; i < pos && iterator != this->_Last; i++) iterator = iterator->next;
		type temp = iterator->data;
		if (this->_First == this->_Last) {
			free(this->_First);
			this->_First = this->_Last = NULL;
		} else if (iterator == this->_First) {
			this->_First = this->_First->next;
			free(this->_First->before);
			this->_First->before = NULL;
		} else if (iterator == this->_Last) {
			this->_Last = this->_Last->before;
			free(this->_Last->next);
			this->_Last->next = NULL;
		} else {
			iterator->before->next = iterator->next;
			iterator->next->before = iterator->before;
			free(iterator);
		}
		return temp;
	}
}
int List$find(List* this, type data) {
	Element* iterator = this->_First;
	for (int i = 0; iterator; i++) {
		if (iterator->data == data) return i;
		iterator = iterator->next;
	}
	return -2;
}

Stack* newStack() {
	Stack* temp = _create(Stack);
	if (temp) {
		temp->_First = temp->_Last = NULL;
		temp->isEmpty = Stack$isEmpty;
		temp->display = Stack$display;
		temp->clear = Stack$clear;
		temp->push = Stack$push;
		temp->pop = Stack$pop;
		temp->top = Stack$top;
	} else _memory_is_full;
	return temp;
}
Queue* newQueue() {
	Queue* temp = _create(Queue);
	if (temp) {
		temp->_First = temp->_Last = NULL;
		temp->isEmpty = Stack$isEmpty;
		temp->display = Stack$display;
		temp->clear = Stack$clear;
		temp->enqueue = Stack$push;
		temp->dequeue = Queue$dequeue;
		temp->bottom = Queue$bottom;
	} else _memory_is_full;
	return temp;
}
Deque* newDeque() {
	Deque* temp = _create(Deque);
	if (temp) {
		temp->_First = temp->_Last = NULL;
		temp->isEmpty = Stack$isEmpty;
		temp->display = Stack$display;
		temp->clear = Stack$clear;
		temp->push = Stack$push;
		temp->pushLeft = Deque$pushLeft;
		temp->pop = Stack$pop;
		temp->popLeft = Queue$dequeue;
		temp->top = Stack$top;
		temp->bottom = Queue$bottom;
	} else _memory_is_full;
	return temp;
}
List* newList() {
	List* temp = _create(List);
	if (temp) {
		temp->_First = temp->_Last = NULL;
		temp->isEmpty = Stack$isEmpty;
		temp->find = List$find;
		temp->display = Stack$display;
		temp->clear = Stack$clear;
		temp->insert = List$insert;
		temp->remove = List$remove;
		temp->top = Stack$top;
		temp->bottom = Queue$bottom;
	} else _memory_is_full;
	return temp;
}

#endif