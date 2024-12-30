let table; // Global variable for the table

$(document).ready(() => {
    // Initialize DataTable
    table = $("#tb-region").DataTable({
        ajax: {
            method: "GET",
            url: "/api/region",
            dataSrc: "",
        },
        columnDefs: [{ className: "text-center", targets: "_all" }],
        columns: [
            { data: "id" },
            { data: "name" },
            {
                data: null,
                render: (data) => {
                    return `
                        <button class="btn btn-success btn-sm" onclick="detailRegion(${data.id})">Detail</button>
                        <button class="btn btn-warning btn-sm" onclick="updateRegion(${data.id})">Update</button>
                        <button class="btn btn-danger btn-sm" onclick="deleteRegion(${data.id})">Delete</button>
                    `;
                }
            }
        ],
    });

    // Handle form submission for creating a region
    $("#saveRegionButton").click(() => {
        const regionName = $("#regionName").val();

        if (regionName) {
            $.ajax({
                url: '/api/region', 
                method: 'POST',
                contentType: 'application/json',
                data: JSON.stringify({ name: regionName }),
                success: function() {
                    $("#createRegionModal").modal('hide'); // Close modal
                    table.ajax.reload(); // Reload table
                    $("#createRegionForm")[0].reset(); // Reset form
                    Swal.fire({
                        icon: 'success',
                        title: 'Region Created!',
                        text: 'The region has been added successfully!'
                    });
                },
                error: function(xhr, status, error) {
                    Swal.fire({
                        icon: 'error',
                        title: 'Error!',
                        text: 'There was an error creating the region.'
                    });
                    console.error('Error creating region:', error);
                }
            });
        } else {
            Swal.fire({
                icon: 'warning',
                title: 'Warning!',
                text: 'Please enter a region name.'
            });
        }
    });

    // Handle Detail button
    window.detailRegion = function(id) {
        $.ajax({
            url: `/api/region/${id}`, 
            method: 'GET',
            success: function(region) {
                $("#detailRegionId").text(region.id);
                $("#detailRegionName").text(region.name);
                $("#detailRegionModal").modal('show'); // Show modal
            },
            error: function(xhr, status, error) {
                console.error('Error fetching region details:', error);
            }
        });
    };

    // Handle Update button
    window.updateRegion = function(id) {
        $.ajax({
            url: `/api/region/${id}`, 
            method: 'GET',
            success: function(region) {
                $("#updateRegionId").val(region.id);
                $("#updateRegionName").val(region.name);
                $("#updateRegionModal").modal('show'); // Show modal
            },
            error: function(xhr, status, error) {
                console.error('Error fetching region for update:', error);
            }
        });
    };

    // Handle form submission for updating a region
    $("#saveUpdateButton").click(() => {
        const id = $("#updateRegionId").val();
        const regionName = $("#updateRegionName").val();

        if (regionName) {
            $.ajax({
                url: `/api/region/${id}`, 
                method: 'PUT',
                contentType: 'application/json',
                data: JSON.stringify({ name: regionName }),
                success: function() {
                    $("#updateRegionModal").modal('hide'); // Close modal
                    table.ajax.reload(); // Reload table
                    Swal.fire({
                        icon: 'success',
                        title: 'Region Updated!',
                        text: 'The region has been updated successfully!'
                    });
                },
                error: function(xhr, status, error) {
                    Swal.fire({
                        icon: 'error',
                        title: 'Error!',
                        text: 'There was an error updating the region.'
                    });
                    console.error('Error updating region:', error);
                }
            });
        } else {
            Swal.fire({
                icon: 'warning',
                title: 'Warning!',
                text: 'Please enter a region name.'
            });
        }
    });
});

// Handle Delete button
window.deleteRegion = function(id) {
    $("#deleteRegionId").val(id); // Store ID in hidden input
    $("#deleteRegionModal").modal('show'); // Show confirmation modal
};

// Handle delete confirmation
$("#confirmDeleteButton").click(() => {
    const id = $("#deleteRegionId").val(); // Get ID from hidden input

    $.ajax({
        url: `/api/region/${id}`, 
        method: 'DELETE',
        success: function() {
            table.ajax.reload(); // Reload data after deletion
            $("#deleteRegionModal").modal('hide'); // Close modal
            Swal.fire({
                icon: 'success',
                title: 'Region Deleted!',
                text: 'The region has been deleted successfully!'
            });
        },
        error: function(xhr, status, error) {
            Swal.fire({
                icon: 'error',
                title: 'Error!',
                text: 'There was an error deleting the region.'
            });
            console.error('Error deleting region:', error);
        }
    });
});