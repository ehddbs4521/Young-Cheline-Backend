package YoungCheline.YoungCheline.util;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ScrollPaginationCollectionUtil<T> {
    private final List<T> itemsWithNextCursor;
    private final int countPerScroll;

    public static <T> ScrollPaginationCollectionUtil<T> of(List<T> itemsWithNextCursor, int size) {
        return new ScrollPaginationCollectionUtil<>(itemsWithNextCursor, size);
    }

    public boolean isLastScroll() {
        return this.itemsWithNextCursor.size() <= countPerScroll;
    }

    public List<T> getCurrentScrollItems() {
        if (isLastScroll()) {
            return this.itemsWithNextCursor;
        }
        return this.itemsWithNextCursor.subList(0, countPerScroll);
    }

    public T getNextCursor() {
        return itemsWithNextCursor.get(countPerScroll - 1);
    }
}
