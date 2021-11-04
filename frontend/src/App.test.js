import { render, screen } from '@testing-library/react';
import App from './App';

test('renders main title', () => {
  render(<App />);
  const linkElement = screen.getByText(/Начни учиться прямо/i);
  expect(linkElement).toBeInTheDocument();
});

// test('renders description', () => {
//   render(<App />);
//   const linkElement = screen.getByText(/Начни учиться прямо/i);
//   expect(linkElement).toBeInTheDocument();
// });

test('renders card buttons text', () => {
  render(<App />);
  const linkElement = screen.getByText(/Веб-справочник/i);
  expect(linkElement).toBeInTheDocument();

  const linkElement = screen.getByText(/Онлайн Кодинг/i);
  expect(linkElement).toBeInTheDocument();

  const linkElement = screen.getByText(/Экзамен/i);
  expect(linkElement).toBeInTheDocument();
});