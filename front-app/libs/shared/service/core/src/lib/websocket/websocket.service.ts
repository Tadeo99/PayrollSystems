import { Injectable } from "@angular/core";
import { Observable, Observer } from 'rxjs';
import { AnonymousSubject } from 'rxjs/internal/Subject';
import { Subject } from 'rxjs';
import { map } from 'rxjs/operators';
//https://www.npmjs.com/package/uuid
import { v4 as uuidv4 } from 'uuid';
import { environment } from '../../../../../environments/environment';

const UUID_GENEADO : string = uuidv4().toString();
const WS_URL_CONSUMER  = environment.apiServicio["WS_URL_CONSUMER"];
const CHAT_URL = WS_URL_CONSUMER +"notificacionSolicitud/" + UUID_GENEADO;

export interface MessageConsumer {
    tipo:string,
    codigoSolicitud : string;
    source: string;
    content: string;
}

@Injectable({
    providedIn: 'root'
})
export class WebsocketService {
    private subject: AnonymousSubject<MessageEvent<any>> | undefined;
    public messages: Subject<MessageConsumer>;

    constructor() {
        localStorage.setItem("jwtkeyWebSockect",UUID_GENEADO);
        this.messages = <Subject<MessageConsumer>>this.connect(CHAT_URL).pipe(
            map(
                (response: MessageEvent): MessageConsumer => {
                    console.log(response.data);
                     const data = JSON.parse(response.data)
                     console.log("data json " + data);
                    return data;
                }
            )
        );
    }

    public connect(url : string): AnonymousSubject<MessageEvent> {
        if (!this.subject) {
            this.subject = this.create(url);
            console.log("Successfully connected: " + url);
        }
        return this.subject;
    }

    private create(url: string): AnonymousSubject<MessageEvent> {
        const ws = new WebSocket(url);
        const observable = new Observable((obs: Observer<MessageEvent>) => {
            ws.onmessage = obs.next.bind(obs);
            ws.onerror = obs.error.bind(obs);
            ws.onclose = obs.complete.bind(obs);
            return ws.close.bind(ws);
        });
        const observer : any = {
            error: null,
            complete: null,
            next: (data: object) => {
                console.log('Message sent to websocket: ', data);
                if (ws.readyState === WebSocket.OPEN) {
                    ws.send(JSON.stringify(data));
                   //ws.send(data.toString());
                }
            }
        };
        return new AnonymousSubject<MessageEvent>(observer, observable);
    }
}