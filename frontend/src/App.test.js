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
  const firstText = screen.getByText(/Веб-справочник/i);
  expect(firstText).toBeInTheDocument();

  const secondText = screen.getByText(/Онлайн Кодинг/i);
  expect(secondText).toBeInTheDocument();

  const thirdText = screen.getByText(/Экзамен/i);
  expect(thirdText).toBeInTheDocument();
});