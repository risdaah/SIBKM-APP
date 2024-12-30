let table; // Global variable for the table

$(document).ready(() => {
    // Initialize DataTable
    table = $("#tb-country").DataTable({
        ajax: {
            method: "GET",
            url: "/api/country",
            dataSrc: "",
        },
        columnDefs: [{ className: "text-center", targets: "_all" }],
        columns: [
            { data: "id" },
            { data: "code" },
            { data: "name" },
            { data: "region.name" }, // Get region name
            {
                data: null,
                render: (data) => {
                    return `
                        <button class="btn btn-success btn-sm" onclick="detailCountry(${data.id})">Detail</button>
                        <button class="btn btn-warning btn-sm" onclick="updateCountry(${data.id})">Update</button>
                        <button class="btn btn-danger btn-sm" onclick="deleteCountry(${data.id})">Delete</button>
                    `;
                }
            }
        ],
    });

    // Fetch regions for dropdown
    $.ajax({
        url: '/api/region', // Endpoint to get regions
        method: 'GET',
        success: function(regions) {
            regions.forEach(region => {
                $("#countryRegion").append(new Option(region.name, region.id));
                $("#updateCountryRegion").append(new Option(region.name, region.id));
            });
        },
        error: function(xhr, status, error) {
            console.error('Error fetching regions:', error);
        }
    });

    // Handle form submission for creating a country
    $("#saveCountryButton").click(() => {
        const countryCode = $("#countryCode").val();
        const countryName = $("#countryName").val();
        const countryRegionId = $("#countryRegion").val();

        if (countryCode && countryName && countryRegionId) {
            $.ajax({
                url: '/api/country',
                method: 'POST',
                contentType: 'application/json',
                data: JSON.stringify({
                    code: countryCode,
                    name: countryName,
                    region: { id: countryRegionId }
                }),
                success: function() {
                    $("#createCountryModal").modal('hide');
                    table.ajax.reload();
                    $("#createCountryForm")[0].reset();
                    Swal.fire({
                        icon: 'success',
                        title: 'Country Created!',
                        text: 'The country has been added successfully!'
                    });
                },
                error: function(xhr, status, error) {
                    Swal.fire({
                        icon: 'error',
                        title: 'Error!',
                        text: 'There was an error creating the country.'
                    });
                    console.error('Error creating country:', error);
                }
            });
        } else {
            Swal.fire({
                icon: 'warning',
                title: 'Warning!',
                text: 'Please fill in all fields.'
            });
        }
    });

    // Handle form submission for updating a country
    $("#saveUpdateCountryButton").click(() => {
        const id = $("#updateCountryId").val();
        const countryCode = $("#updateCountryCode").val();
        const countryName = $("#updateCountryName").val();
        const countryRegionId = $("#updateCountryRegion").val();

        if (countryCode && countryName && countryRegionId) {
            $.ajax({
                url: `/api/country/${id}`, // URL to update country
                method: 'PUT',
                contentType: 'application/json',
                data: JSON.stringify({
                    code: countryCode,
                    name: countryName,
                    region: { id: countryRegionId }
                }),
                success: function() {
                    $("#updateCountryModal").modal('hide');
                    table.ajax.reload();
                    Swal.fire({
                        icon: 'success',
                        title: 'Country Updated!',
                        text: 'The country has been updated successfully!'
                    });
                },
                error: function(xhr, status, error) {
                    Swal.fire({
                        icon: 'error',
                        title: 'Error!',
                        text: 'There was an error updating the country.'
                    });
                    console.error('Error updating country:', error);
                }
            });
        } else {
            Swal.fire({
                icon: 'warning',
                title: 'Warning!',
                text: 'Please fill in all fields.'
            });
        }
    });
});

// Handle Detail button
window.detailCountry = function(id) {
    $.ajax({
        url: `/api/country/${id}`, // Endpoint to get country by ID
        method: 'GET',
        success: function(country) {
            $("#detailCountryId").text(country.id);
            $("#detailCountryCode").text(country.code);
            $("#detailCountryName").text(country.name);
            $("#detailCountryRegion").text(country.region.name); // Show region name
            $("#detailCountryModal").modal('show');
        },
        error: function(xhr, status, error) {
            console.error('Error fetching country details:', error);
        }
    });
};

// Handle Update button
window.updateCountry = function(id) {
    $.ajax({
        url: `/api/country/${id}`, // Endpoint to get country by ID
        method: 'GET',
        success: function(country) {
            $("#updateCountryId").val(country.id);
            $("#updateCountryCode").val(country.code);
            $("#updateCountryName").val(country.name);
            $("#updateCountryRegion").val(country.region.id); // Set region ID in dropdown
            $("#updateCountryModal").modal('show');
        },
        error: function(xhr, status, error) {
            console.error('Error fetching country for update:', error);
        }
    });
};

// Handle Delete button
window.deleteCountry = function(id) {
    $("#deleteCountryId").val(id); // Store ID in hidden input
    $("#deleteCountryModal").modal('show'); // Show confirmation modal
};

// Handle delete confirmation
$("#confirmDeleteButton").click(() => {
    const id = $("#deleteCountryId").val(); // Get ID from hidden input

    $.ajax({
        url: `/api/country/${id}`, // URL to delete country
        method: 'DELETE',
        success: function() {
            table.ajax.reload(); // Reload data after deletion
            $("#deleteCountryModal").modal('hide'); // Close modal after deletion
            Swal.fire({
                icon: 'success',
                title: 'Country Deleted!',
                text: 'The country has been deleted successfully!'
            });
        },
        error: function(xhr, status, error) {
            Swal.fire({
                icon: 'error',
                title: 'Error!',
                text: 'There was an error deleting the country.'
            });
            console.error('Error deleting country:', error);
        }
    });
});