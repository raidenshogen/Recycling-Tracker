import { Injectable, signal } from '@angular/core';
import { Router } from '@angular/router';
import { Observable, tap } from 'rxjs';
import { EcopointsService } from './ecopoints.service';
import { AuthPayload, Role } from './models';

interface StoredSession {
  token: string;
  role: Role;
  username: string;
  householdId: number | null;
  expiresAt: number;
}

const STORAGE_KEY = 'eco_session';

@Injectable({ providedIn: 'root' })
export class AuthService {
  // Signals so the navbar/guards can react instantly without polling.
  session = signal<StoredSession | null>(this.readSession());

  constructor(private eco: EcopointsService, private router: Router) {}

  get isAuthenticated(): boolean {
    const s = this.session();
    return !!s && s.expiresAt > Date.now();
  }

  get role(): Role | null {
    return this.session()?.role ?? null;
  }

  get isAdmin(): boolean {
    return this.role === 'ADMIN';
  }

  get token(): string | null {
    return this.isAuthenticated ? this.session()!.token : null;
  }

  login(username: string, password: string): Observable<AuthPayload> {
    return this.eco.login(username, password).pipe(
      tap((payload) => {
        const session: StoredSession = {
          token: payload.token,
          role: payload.role,
          username,
          householdId: payload.householdId,
          expiresAt: this.decodeExpiry(payload.token),
        };
        sessionStorage.setItem(STORAGE_KEY, JSON.stringify(session));
        this.session.set(session);
      })
    );
  }

  logout(): void {
    sessionStorage.removeItem(STORAGE_KEY);
    this.session.set(null);
    this.router.navigateByUrl('/login');
  }

  private readSession(): StoredSession | null {
    const raw = sessionStorage.getItem(STORAGE_KEY);
    if (!raw) return null;
    try {
      const parsed: StoredSession = JSON.parse(raw);
      return parsed.expiresAt > Date.now() ? parsed : null;
    } catch {
      return null;
    }
  }

  /** Reads the `exp` claim out of the JWT payload (base64url, no verification —
   *  verification happens server-side; this is only for UI expiry checks). */
  private decodeExpiry(token: string): number {
    try {
      const payload = JSON.parse(atob(token.split('.')[1].replace(/-/g, '+').replace(/_/g, '/')));
      return payload.exp ? payload.exp * 1000 : Date.now() + 60 * 60 * 1000;
    } catch {
      return Date.now() + 60 * 60 * 1000;
    }
  }
}
