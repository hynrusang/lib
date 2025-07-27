#ifndef OBJECTVIEW_H_
#define OBJECTVIEW_H_

#include <array>
#include <cstddef>

template <typename T>
struct ObjectView {
	T* const objects;
	const std::size_t size;
};

template <typename T, typename... Ptrs>
constexpr ObjectView<T> createObjectView(Ptrs*... ptrs) noexcept {
	static std::array<T*, sizeof...(Ptrs)> objects = { ptrs... };
	return { objects.data(), objects.size() };
}

#endif
