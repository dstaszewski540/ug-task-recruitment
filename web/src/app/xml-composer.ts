import {Items} from "./item";

export class XmlComposer {
  static export(items: Items) {
    var a = document.createElement("a");
    var file = new Blob([this.makeRAW(this.create(items))], {type: "text/xml"})
    a.href = URL.createObjectURL(file);
    a.download = "data.xml";
    a.onclick = () => a.remove();
    a.click();
  }

  private static makeRAW(doc: XMLDocument): string {
    return new XMLSerializer().serializeToString(doc)
  }

  private static create(items: Items) {
    var doc = document.implementation.createDocument("", "", null);
    var root = doc.createElement("faktura");
    items.forEach(item => {
      var k = doc.createElement("komputer");
      var n = doc.createElement("nazwa");
      n.append(item.name);
      var d = doc.createElement("data_ksiegowania");
      d.append(item.date);
      var u = doc.createElement("kwota_usd");
      d.append(item.usd.toFixed(2))
      var p = doc.createElement("kwota_pln");
      d.append(item.pln.toFixed(2))
      k.appendChild(n);
      k.appendChild(d);
      k.appendChild(u);
      k.appendChild(p);
      root.appendChild(k);
    })
    doc.appendChild(root);
    return doc;
  }
}
