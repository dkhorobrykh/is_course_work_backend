<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>${msg("loginTitle")}</title>
    <link rel="stylesheet" href="${url.resourcesPath}/css/style.css">
</head>
<body>
<div class="background"></div>
<canvas id="particles"></canvas>

<div class="login-container">
    <h1 class="logo">Brand</h1>
    <form id="kc-form-login" action="${url.loginAction}" method="post">
        <div class="input-group">
            <input type="text" name="username" placeholder="Username" required autofocus>
            <span class="highlight"></span>
        </div>
        <div class="input-group">
            <input type="password" name="password" placeholder="Password" required>
            <span class="highlight"></span>
        </div>
        <button type="submit">Log In</button>
    </form>
    <a href="${url.registrationUrl}" class="link">Sign Up</a>
</div>

<script>
    const inputs = document.querySelectorAll('input');
    inputs.forEach(input => {
        input.addEventListener('focus', () => input.parentNode.classList.add('active'));
        input.addEventListener('blur', () => input.parentNode.classList.remove('active'));
    });

    // Logo subtle rotation
    const logo = document.querySelector('.logo');
    document.addEventListener('mousemove', e => {
        const x = (window.innerWidth/2 - e.clientX)/80;
        const y = (window.innerHeight/2 - e.clientY)/80;
        logo.style.transform = 'rotateX(' + y + 'deg) rotateY(' + x + 'deg)';
    });

    // Particles
    const canvas = document.getElementById('particles');
    const ctx = canvas.getContext('2d');
    let particlesArray;
    canvas.width = window.innerWidth;
    canvas.height = window.innerHeight;

    class Particle {
        constructor(x,y,size,color,dx,dy){
            this.x=x; this.y=y; this.size=size; this.color=color; this.dx=dx; this.dy=dy;
        }
        draw(){
            ctx.beginPath();
            ctx.arc(this.x,this.y,this.size,0,Math.PI*2);
            ctx.fillStyle=this.color;
            ctx.fill();
        }
        update(){
            if(this.x+this.size>canvas.width || this.x-this.size<0) this.dx*=-1;
            if(this.y+this.size>canvas.height || this.y-this.size<0) this.dy*=-1;
            this.x+=this.dx;
            this.y+=this.dy;
            this.draw();
        }
    }

    function initParticles(){
        particlesArray=[];
        for(let i=0;i<80;i++){
            let size=Math.random()*2+1;
            let x=Math.random()*canvas.width;
            let y=Math.random()*canvas.height;
            let dx=(Math.random()-0.5)*0.7;
            let dy=(Math.random()-0.5)*0.7;
            particlesArray.push(new Particle(x,y,size,'rgba(255,255,255,0.1)',dx,dy));
        }
    }

    function animateParticles(){
        requestAnimationFrame(animateParticles);
        ctx.clearRect(0,0,canvas.width,canvas.height);
        particlesArray.forEach(p=>p.update());
    }

    initParticles();
    animateParticles();

    // Form floats subtly
    const form = document.querySelector('.login-container');
    document.addEventListener('mousemove', e=>{
        const x = (e.clientX - window.innerWidth/2)/100;
        const y = (e.clientY - window.innerHeight/2)/100;
        form.style.transform = 'translate(' + x + 'px,' + y + 'px)';
    });
</script>
</body>
</html>
