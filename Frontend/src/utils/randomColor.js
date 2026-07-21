const colors = [
  "magenta",
  "red",
  "volcano",
  "orange",
  "gold",
  "green",
  "blue",
  "purple",
];

export const getRandomColor = () => {
  return colors[Math.floor(Math.random() * colors.length)];
};