import {Link} from "react-router-dom";

export default function WelcomePage() {
    return <div className="welcome-page">
        <section className="welcome-hero">
            <div className="container welcome-hero-inner">
                <div className="welcome-copy">
                    <span className="eyebrow">DEIN NÄCHSTES KAPITEL BEGINNT HIER</span>
                    <h1>Geschichten, die<br/><span>bei dir bleiben.</span></h1>
                    <p>Entdecke neue Lieblingsbücher, stöbere durch spannende Genres und bewahre deinen Warenkorb für
                        deinen nächsten Besuch auf.</p>
                    <div className="d-flex flex-wrap gap-3 mt-4">
                        <Link to="/register" className="btn btn-booki-primary btn-lg">Konto erstellen <span
                            aria-hidden="true">↗</span></Link>
                        <Link to="/login" className="btn btn-booki-glass btn-lg">Ich habe schon ein Konto</Link>
                    </div>
                    <div className="welcome-trust"><span>✦</span> Deine persönliche Bücherwelt – jederzeit griffbereit
                    </div>
                </div>
                <div className="welcome-art" aria-label="Dekorative Buchillustration">
                    <div className="orbit orbit-one"/>
                    <div className="orbit orbit-two"/>
                    <div className="book-stack book-back"><span>IDEEN, DIE BLEIBEN</span></div>
                    <div className="book-stack book-front"><small>BOOKI-BOOKI EDITION</small><strong>Ein neues<br/>Kapitel</strong><span>Jede Seite ein Abenteuer.</span><i>✦</i>
                    </div>
                    <div className="floating-note note-top">Dein nächster Favorit ✨</div>
                    <div className="floating-note note-bottom">Lesen. Entdecken. Wiederkommen.</div>
                </div>
            </div>
        </section>
        <section className="container welcome-features">
            <div className="welcome-feature"><span className="feature-icon">⌕</span>
                <div><h2>Entdecken</h2><p>Finde Bücher, die zu deiner Stimmung passen.</p></div>
            </div>
            <div className="welcome-feature"><span className="feature-icon">♡</span>
                <div><h2>Merken</h2><p>Dein Warenkorb bleibt mit deinem Konto gespeichert.</p></div>
            </div>
            <div className="welcome-feature"><span className="feature-icon">↗</span>
                <div><h2>Weiterlesen</h2><p>Melde dich an und mach genau dort weiter, wo du warst.</p></div>
            </div>
        </section>
    </div>;
}
