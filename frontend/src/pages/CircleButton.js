import React, { useState } from 'react';
import { useRouter } from 'next/router';

export default function CircleButton({ size = 120, bgColor = '#4caf50', hoverColor = '#45a049', iconUrl, children }) {
    const [hover, setHover] = useState(false);
    const router = useRouter();

    const handleClick = () => {
        router.push('/login');
    };

    return (
        <button
            onClick={handleClick}
            style={{
                width: size,
                height: size,
                borderRadius: '50%',
                backgroundColor: hover ? hoverColor : bgColor,
                display: 'flex',
                flexDirection: 'column',
                alignItems: 'center',
                justifyContent: 'center',
                border: 'none',
                cursor: 'pointer',
            }}
            onMouseEnter={() => setHover(true)}
            onMouseLeave={() => setHover(false)}
        >
            <img
                src="bootcamp-2025.03-logo.jpg"
                alt="Icon"
                style={{ width: '50%', height: 'auto', marginBottom: '8px' }}
            />
            <span>{children || 'Click me'}</span>
        </button>
    );
}
