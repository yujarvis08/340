#include <iostream>
#include <cstdlib>
#include <ctime>
#include <memory>
#include "LinkedBag.h"

template<typename ItemType>
bool LinkedBag<ItemType>::removeSecondNode340() {

	auto tempNode = headPtr;
	tempNode = headPtr->getNext();
	remove(tempNode->getItem());
	return true;

}

template<typename ItemType>
bool LinkedBag<ItemType>::addEnd340(const ItemType& thing) {

	auto tempNode = headPtr;
	while (tempNode->getNext() != nullptr) {
		tempNode = tempNode->getNext();
	}
	if (tempNode->getNext() == nullptr) {
		auto newNode = new Node<ItemType>();
		newNode->setItem(thing);
		tempNode->setNext(newNode);
		itemCount++;
	}
	return true;

}

template<typename ItemType>
int LinkedBag<ItemType>::getCurrentSize340Iterative() const{

	auto tempNode = headPtr;
	int bagSize = 0;

		while (tempNode != nullptr) {
			bagSize++;
			tempNode = tempNode->getNext();
		}
	return bagSize;

}

template<typename ItemType>
int LinkedBag<ItemType>::getCurrentSize340Recursive() const{

	auto tempNode = headPtr;
	return getCurrentSize340RecursiveHelper(tempNode);

}

template<typename ItemType>
int LinkedBag<ItemType>::getCurrentSize340RecursiveHelper(Node<ItemType>* node) const{

		if (node == nullptr) {
			return 0;
		}
		else {
			auto duplicate = LinkedBag(*this);
			duplicate.remove(duplicate.headPtr->getItem());
			return 1 + getCurrentSize340RecursiveHelper(node->getNext());
		}
}

template<typename ItemType>
int LinkedBag<ItemType>::getCurrentSize340RecursiveNoHelper() const{

	auto tempNode = headPtr;
		if (tempNode == nullptr) {
			return 0;
		}
		else {
			auto duplicate = LinkedBag(*this);
			duplicate.remove(duplicate.headPtr->getItem());
			return 1 + duplicate.getCurrentSize340RecursiveNoHelper();
		}
}

template<typename ItemType>
int LinkedBag<ItemType>::getFrequencyOf340Recursive(const ItemType& thing) const{
	auto tempNode = headPtr;
	return getFrequencyOf340RecursiveHelper(tempNode, thing);
}

template<typename ItemType>
int LinkedBag<ItemType>::getFrequencyOf340RecursiveHelper(Node<ItemType>* node, const ItemType& thing) const{
	if (node == nullptr) {
		return 0;
	}
	if (node->getItem() == thing) {
		return 1 + getFrequencyOf340RecursiveHelper(node->getNext(), thing);
	}
	else {
		return getFrequencyOf340RecursiveHelper(node->getNext(), thing);
	}
}

template<typename ItemType>
int LinkedBag<ItemType>::getFrequencyOf340RecursiveNoHelper(const ItemType& thing) const {
	static auto tempNode = headPtr;
	int cases = 0;
	if (tempNode == nullptr) {
		tempNode = headPtr;
		return 0;
	}
	if (tempNode->getItem() == thing) {
		cases++;
		tempNode = tempNode->getNext();
		return cases + getFrequencyOf340RecursiveNoHelper(thing);
	}
	else {
		tempNode = tempNode->getNext();
		return cases + getFrequencyOf340RecursiveNoHelper(thing);
	}
}

template<typename ItemType>
ItemType LinkedBag<ItemType>::removeRandom340() {
	int random = 0;
	int bagSize = getCurrentSize340Iterative();
	int min = 1;
	ItemType nodeLoc;

	for (int i = 1; i <= bagSize; i++) {
		random = rand() % bagSize + min;
	}

	auto tempNode = headPtr;
	int nElement = random;

	for (int i = 0; i < (nElement - 2); i++) {
		tempNode = tempNode->getNext();
	}
	auto tempNode2 = tempNode->getNext();
	nodeLoc = tempNode2->getItem();
	remove(nodeLoc);
	return nodeLoc;
}