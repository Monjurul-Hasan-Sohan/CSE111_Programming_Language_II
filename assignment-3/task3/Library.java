package task3;

class Library {
    private String[] books;
    private int capacity;
    private int count;

    Library() {
        capacity = 0;
        count = 0;
    }

    void setBookCapacity(int cap) {
        this.capacity = cap;
        books = new String[cap];
    }

    void addBook(String bookName) {
        if (count < capacity) {
            books[count++] = bookName;
            System.out.println("Book '" + bookName + "' added to the library");
        } else {
            System.out.println("Exceeds maximum capacity. You can't add more than " + capacity + " books");
        }
    }

    void printDetail() {
        System.out.println("Maximum Capacity: " + capacity);
        System.out.println("Total Books: " + count);
        System.out.println("Book list:");
        for (int i = 0; i < count; i++) {
            System.out.println(books[i]);
        }
    }
}