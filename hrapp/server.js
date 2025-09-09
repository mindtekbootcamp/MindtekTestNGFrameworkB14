const express = require('express');
const cors = require('cors');
const { Pool } = require('pg');

const app = express();
const port = 3000;

const pool = new Pool({
  user: 'postgres',
  host: 'localhost',
  database: 'HR',
  password: 'Admin123',
  port: 5432,
});

app.use(cors());
app.use(express.json());

// --- Validation Helpers ---
function validateEmployeeInput(data) {
  const { first_name, last_name, email, hire_date, job_id, salary, department_id } = data;
  if (!first_name || !last_name || !email || !hire_date || !job_id || !salary || !department_id) {
    return 'All employee fields are required';
  }
  if (isNaN(salary)) return 'Salary should be a number';
  if (isNaN(department_id)) return 'Department ID should be a number';
  if (isNaN(job_id)) return 'Job ID should be a number';
  return null;
}

function validateDepartmentInput(data) {
  const { name, location_id } = data;
  if (!name || !location_id) return 'Department name and location_id are required';
  if (isNaN(location_id)) return 'Location ID should be a number';
  return null;
}

// --- Check existence helpers ---
async function employeeExists(id) {
  const result = await pool.query('SELECT 1 FROM employees WHERE employee_id = $1', [id]);
  return result.rows.length > 0;
}

async function departmentExists(id) {
  const result = await pool.query('SELECT 1 FROM departments WHERE department_id = $1', [id]);
  return result.rows.length > 0;
}

// --- Departments APIs ---
app.get('/api/departments', async (req, res) => {
  try {
    const { limit, order } = req.query;
    let query = 'SELECT * FROM departments';
    const values = [];

    // Only apply ORDER BY if order is specified
    if (order && ['asc', 'desc'].includes(order.toLowerCase())) {
      query += ` ORDER BY department_name ${order.toUpperCase()}`;
    }

    // Apply LIMIT if provided
    if (limit && !isNaN(limit)) {
      query += values.length ? ` LIMIT $${values.length + 1}` : ` LIMIT $1`;
      values.push(parseInt(limit));
    }

    const result = await pool.query(query, values);
    res.json(result.rows);
  } catch (err) {
    console.error('Error fetching departments:', err);
    res.status(500).json({ error: 'Internal Server Error' });
  }
});

app.get('/api/departments/:id', async (req, res) => {
  try {
    const result = await pool.query('SELECT * FROM departments WHERE department_id = $1', [req.params.id]);
    if (result.rows.length === 0) return res.status(404).json({ error: 'Department not found' });
    res.json(result.rows[0]);
  } catch (err) {
    console.error(err);
    res.status(500).json({ error: 'Internal Server Error while fetching department' });
  }
});

app.post('/api/departments', async (req, res) => {
  const validationError = validateDepartmentInput(req.body);
  if (validationError) return res.status(400).json({ error: validationError });

  try {
    const result = await pool.query(
      'INSERT INTO departments (department_name, location_id) VALUES ($1, $2) RETURNING *',
      [req.body.name, req.body.location_id]
    );
    res.status(201).json(result.rows[0]);
  } catch (err) {
    console.error(err);
    res.status(500).json({ error: 'Internal Server Error while creating department' });
  }
});

app.put('/api/departments/:id', async (req, res) => {
  const { id } = req.params;
  const validationError = validateDepartmentInput(req.body);
  if (validationError) return res.status(400).json({ error: validationError });

  try {
    const exists = await departmentExists(id);
    if (exists) {
      const result = await pool.query(
        'UPDATE departments SET department_name = $1, location_id = $2 WHERE department_id = $3 RETURNING *',
        [req.body.name, req.body.location_id, id]
      );
      res.json(result.rows[0]);
    } else {
      const result = await pool.query(
        'INSERT INTO departments (department_id, department_name, location_id) VALUES ($1, $2, $3) RETURNING *',
        [id, req.body.name, req.body.location_id]
      );
      res.status(201).json(result.rows[0]);
    }
  } catch (err) {
    console.error(err);
    res.status(500).json({ error: 'Internal Server Error while upserting department' });
  }
});

app.delete('/api/departments/:id', async (req, res) => {
  try {
    const result = await pool.query('DELETE FROM departments WHERE department_id = $1 RETURNING *', [req.params.id]);
    if (result.rows.length === 0) return res.status(404).json({ error: 'Department not found' });
    res.status(204).send();
  } catch (err) {
    console.error(err);
    res.status(500).json({ error: 'Internal Server Error while deleting department' });
  }
});

// --- Employees APIs ---
app.get('/api/employees', async (req, res) => {
  try {
    const { limit, order } = req.query;
    let query = 'SELECT * FROM employees';
    const values = [];

    // Only apply ORDER BY if order is specified
    if (order && ['asc', 'desc'].includes(order.toLowerCase())) {
      query += ` ORDER BY first_name ${order.toUpperCase()}`;
    }

    // Apply LIMIT if provided
    if (limit && !isNaN(limit)) {
      query += values.length ? ` LIMIT $${values.length + 1}` : ` LIMIT $1`;
      values.push(parseInt(limit));
    }

    const result = await pool.query(query, values);
    res.json(result.rows);
  } catch (err) {
    console.error('Error fetching employees:', err);
    res.status(500).json({ error: 'Internal Server Error' });
  }
});

app.get('/api/employees/:id', async (req, res) => {
  try {
    const result = await pool.query('SELECT * FROM employees WHERE employee_id = $1', [req.params.id]);
    if (result.rows.length === 0) return res.status(404).json({ error: 'Employee not found' });
    res.json(result.rows[0]);
  } catch (err) {
    console.error(err);
    res.status(500).json({ error: 'Internal Server Error while fetching employee' });
  }
});

app.post('/api/employees', async (req, res) => {
  const validationError = validateEmployeeInput(req.body);
  if (validationError) return res.status(400).json({ error: validationError });

  try {
    const result = await pool.query(
      `INSERT INTO employees 
       (first_name, last_name, email, hire_date, job_id, salary, department_id) 
       VALUES ($1, $2, $3, $4, $5, $6, $7) RETURNING *`,
      [req.body.first_name, req.body.last_name, req.body.email, req.body.hire_date,
        req.body.job_id, req.body.salary, req.body.department_id]
    );
    res.status(201).json(result.rows[0]);
  } catch (err) {
    console.error(err);
    res.status(500).json({ error: 'Internal Server Error while creating employee' });
  }
});

app.put('/api/employees/:id', async (req, res) => {
  const { id } = req.params;
  const validationError = validateEmployeeInput(req.body);
  if (validationError) return res.status(400).json({ error: validationError });

  try {
    const exists = await employeeExists(id);
    if (exists) {
      const result = await pool.query(
        `UPDATE employees SET 
        first_name=$1, last_name=$2, email=$3, hire_date=$4, job_id=$5, salary=$6, department_id=$7 
        WHERE employee_id=$8 RETURNING *`,
        [req.body.first_name, req.body.last_name, req.body.email, req.body.hire_date,
          req.body.job_id, req.body.salary, req.body.department_id, id]
      );
      res.json(result.rows[0]);
    } else {
      const result = await pool.query(
        `INSERT INTO employees (employee_id, first_name, last_name, email, hire_date, job_id, salary, department_id)
        VALUES ($1, $2, $3, $4, $5, $6, $7, $8) RETURNING *`,
        [id, req.body.first_name, req.body.last_name, req.body.email, req.body.hire_date,
          req.body.job_id, req.body.salary, req.body.department_id]
      );
      res.status(201).json(result.rows[0]);
    }
  } catch (err) {
    console.error(err);
    res.status(500).json({ error: 'Internal Server Error while upserting employee' });
  }
});

app.delete('/api/employees/:id', async (req, res) => {
  try {
    const result = await pool.query('DELETE FROM employees WHERE employee_id = $1 RETURNING *', [req.params.id]);
    if (result.rows.length === 0) return res.status(404).json({ error: 'Employee not found' });
    res.status(204).send();
  } catch (err) {
    console.error(err);
    res.status(500).json({ error: 'Internal Server Error while deleting employee' });
  }
});

app.listen(port, () => {
  console.log(`Server is running on http://localhost:${port}`);
});