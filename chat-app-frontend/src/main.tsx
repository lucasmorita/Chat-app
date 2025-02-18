// import { scan } from "react-scan";
import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import App from './App.tsx'

// scan({
//   enabled: true
// })

createRoot(document.getElementById('root')!).render(
  <StrictMode>
    <App />
  </StrictMode>
)

// createRoot(document.getElementById('header')!).render(<NavBar />);
