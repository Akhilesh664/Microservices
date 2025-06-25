document.addEventListener('DOMContentLoaded', () => {
    // Fetch reviews on page load
    fetchReviews();

    // Handle form submission
    document.getElementById('review-form').addEventListener('submit', async (e) => {
        e.preventDefault();

        const productId = document.getElementById('productId').value;
        const reviewText = document.getElementById('reviewText').value;
        const message = document.getElementById('message');

        if (!productId || !reviewText) {
            message.textContent = 'Please fill in both fields.';
            return;
        }

        try {
            const response = await fetch('http://localhost:8080/api/reviews', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({ productId, reviewText })
            });

            if (response.ok) {
                message.textContent = 'Review submitted successfully! Sentiment analysis in progress.';
                document.getElementById('productId').value = '';
                document.getElementById('reviewText').value = '';
                fetchReviews(); // Refresh reviews
            } else {
                message.textContent = 'Error submitting review.';
            }
        } catch (error) {
            message.textContent = 'Error submitting review.';
            console.error('Error:', error);
        }
    });
});

async function fetchReviews() {
    try {
        const response = await fetch('http://localhost:8080/api/reviews');
        const reviews = await response.json();
        const reviewsBody = document.getElementById('reviews-body');
        reviewsBody.innerHTML = '';

        if (reviews.length === 0) {
            reviewsBody.innerHTML = '<tr><td colspan="4">No reviews yet.</td></tr>';
            return;
        }

        reviews.forEach(review => {
            const row = document.createElement('tr');
            row.innerHTML = `
                <td>${review.productId}</td>
                <td>${review.reviewText}</td>
                <td>${review.sentiment}</td>
                <td>${new Date(review.createdAt).toLocaleString()}</td>
            `;
            reviewsBody.appendChild(row);
        });
    } catch (error) {
        console.error('Error fetching reviews:', error);
    }
}