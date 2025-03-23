const API_URL = "/api/books/search";
let timeout = null; // Used for debouncing

document.addEventListener("DOMContentLoaded", () => {
    fetchBooks("Spring Boot"); // Default books on load
});

// Fetch books from Spring API
async function fetchBooks(query) {
    try {
        document.getElementById("bookCatalog").innerHTML = `<div class="loading"></div>`; // Show loading

        const response = await fetch(`${API_URL}?query=${encodeURIComponent(query)}&maxResults=10`);
        const data = await response.json();

        if (data.items) {
            renderBooks(data.items);
        } else {
            document.getElementById("bookCatalog").innerHTML = "<p>No books found.</p>";
        }
    } catch (error) {
        console.error("Error fetching books:", error);
        document.getElementById("bookCatalog").innerHTML = "<p>Failed to load books.</p>";
    }
}

// Render books dynamically
function renderBooks(books) {
    const catalog = document.getElementById("bookCatalog");
    catalog.innerHTML = ""; // Clear previous results

    books.forEach(book => {
        const volume = book.volumeInfo;
        const bookHTML = `
            <div class="book-card">
                <img src="${volume.imageLinks?.thumbnail || '/images/default-book.png'}" alt="Book Cover">
                <h3>${volume.title}</h3>
                <p>${volume.authors ? volume.authors.join(", ") : "Unknown Author"}</p>
                <a href="${volume.infoLink}" target="_blank" class="details-btn">View Details</a>
            </div>
        `;
        catalog.innerHTML += bookHTML;
    });
}

// Debounced Search Function
function debouncedSearch() {
    clearTimeout(timeout);
    timeout = setTimeout(() => {
        const query = document.getElementById("searchBox").value.trim();
        if (query.length > 2) { // Avoid unnecessary API calls
            fetchBooks(query);
        }
    }, 500);
}
