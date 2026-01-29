document.addEventListener('DOMContentLoaded', () => {
    const addStudentForm = document.getElementById('addStudentForm');
    const studentTableBody = document.querySelector('#studentTable tbody');

    // Function to fetch and display students
    async function fetchStudents() {
        const response = await fetch('/students');
        const students = await response.json();
        studentTableBody.innerHTML = ''; // Clear existing table rows

        students.forEach(student => {
            const row = studentTableBody.insertRow();
            row.insertCell(0).textContent = student.id;
            row.insertCell(1).textContent = student.name;
            row.insertCell(2).textContent = student.email;
            row.insertCell(3).textContent = student.department;

            const actionsCell = row.insertCell(4);
            const editButton = document.createElement('button');
            editButton.textContent = 'Edit';
            editButton.addEventListener('click', () => editStudent(student));
            actionsCell.appendChild(editButton);

            const deleteButton = document.createElement('button');
            deleteButton.textContent = 'Delete';
            deleteButton.addEventListener('click', () => deleteStudent(student.id));
            actionsCell.appendChild(deleteButton);
        });
    }

    // Function to add a new student
    addStudentForm.addEventListener('submit', async (e) => {
        e.preventDefault();
        const name = document.getElementById('name').value;
        const email = document.getElementById('email').value;
        const department = document.getElementById('department').value;

        const newStudent = { name, email, department };

        await fetch('/students', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(newStudent),
        });

        addStudentForm.reset();
        fetchStudents(); // Refresh the list
    });

    // Function to edit a student (pre-fill form or show modal)
    function editStudent(student) {
        // For simplicity, let's prompt for new values. In a real app, you'd use a modal or a dedicated edit form.
        const newName = prompt('Enter new name:', student.name);
        const newEmail = prompt('Enter new email:', student.email);
        const newDepartment = prompt('Enter new department:', student.department);

        if (newName !== null && newEmail !== null && newDepartment !== null) {
            const updatedStudent = {
                id: student.id,
                name: newName,
                email: newEmail,
                department: newDepartment
            };
            updateStudentInBackend(updatedStudent);
        }
    }

    // Function to update student in backend
    async function updateStudentInBackend(student) {
        await fetch(`/students/${student.id}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(student),
        });
        fetchStudents(); // Refresh the list
    }

    // Function to delete a student
    async function deleteStudent(id) {
        if (confirm('Are you sure you want to delete this student?')) {
            await fetch(`/students/${id}`, {
                method: 'DELETE',
            });
            fetchStudents(); // Refresh the list
        }
    }

    // Initial fetch of students when the page loads
    fetchStudents();
});
