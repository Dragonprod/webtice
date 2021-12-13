import axios from 'axios';

// export default axios.create({
//   baseURL: `http://89.223.64.28:8080/api`
// });

export default axios.create({
  baseURL: process.env.PUBLIC_URL || `http://localhost:8080/api`
});